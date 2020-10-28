package org.springframework.boot.actuate.autoconfigure.trace.http;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.actuate.trace.http.Include;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for HTTP tracing.
 *





 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "management.trace.http")
public class HttpTraceProperties {

	/**
	 * Items to be included in the trace. Defaults to request headers (excluding
	 * Authorization and Cookie), response headers (excluding Set-Cookie), and time taken.
	 */
	private Set<Include> include = new HashSet<>(Include.defaultIncludes());

	public Set<Include> getInclude() {
		return this.include;
	}

	public void setInclude(Set<Include> include) {
		this.include = include;
	}

}
