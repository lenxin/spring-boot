package org.springframework.boot.actuate.autoconfigure.endpoint.jmx;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.springframework.boot.actuate.endpoint.jmx.EndpointObjectNameFactory;
import org.springframework.boot.actuate.endpoint.jmx.ExposableJmxEndpoint;
import org.springframework.core.env.Environment;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * A {@link EndpointObjectNameFactory} that generates standard {@link ObjectName} for
 * Actuator's endpoints.
 *

 */
class DefaultEndpointObjectNameFactory implements EndpointObjectNameFactory {

	private final JmxEndpointProperties properties;

	private final Environment environment;

	private final MBeanServer mBeanServer;

	private final String contextId;

	private final boolean uniqueNames;

	DefaultEndpointObjectNameFactory(JmxEndpointProperties properties, Environment environment, MBeanServer mBeanServer,
			String contextId) {
		this.properties = properties;
		this.environment = environment;
		this.mBeanServer = mBeanServer;
		this.contextId = contextId;
		this.uniqueNames = environment.getProperty("spring.jmx.unique-names", Boolean.class, false);
	}

	@Override
	public ObjectName getObjectName(ExposableJmxEndpoint endpoint) throws MalformedObjectNameException {
		StringBuilder builder = new StringBuilder(determineDomain());
		builder.append(":type=Endpoint");
		builder.append(",name=").append(StringUtils.capitalize(endpoint.getEndpointId().toString()));
		String baseName = builder.toString();
		if (this.mBeanServer != null && hasMBean(baseName)) {
			builder.append(",context=").append(this.contextId);
		}
		if (this.uniqueNames) {
			String identity = ObjectUtils.getIdentityHexString(endpoint);
			builder.append(",identity=").append(identity);
		}
		builder.append(getStaticNames());
		return ObjectNameManager.getInstance(builder.toString());
	}

	private String determineDomain() {
		if (StringUtils.hasText(this.properties.getDomain())) {
			return this.properties.getDomain();
		}
		return this.environment.getProperty("spring.jmx.default-domain", "org.springframework.boot");
	}

	private boolean hasMBean(String baseObjectName) throws MalformedObjectNameException {
		ObjectName query = new ObjectName(baseObjectName + ",*");
		return !this.mBeanServer.queryNames(query, null).isEmpty();
	}

	private String getStaticNames() {
		if (this.properties.getStaticNames().isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		this.properties.getStaticNames()
				.forEach((name, value) -> builder.append(",").append(name).append("=").append(value));
		return builder.toString();
	}

}
