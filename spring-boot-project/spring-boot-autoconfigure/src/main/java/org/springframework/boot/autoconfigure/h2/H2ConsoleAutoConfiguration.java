package org.springframework.boot.autoconfigure.h2;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.server.web.WebServlet;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties.Settings;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for H2's web console.
 *



 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(WebServlet.class)
@ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true", matchIfMissing = false)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(H2ConsoleProperties.class)
public class H2ConsoleAutoConfiguration {

	private static final Log logger = LogFactory.getLog(H2ConsoleAutoConfiguration.class);

	@Bean
	public ServletRegistrationBean<WebServlet> h2Console(H2ConsoleProperties properties,
			ObjectProvider<DataSource> dataSource) {
		String path = properties.getPath();
		String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
		ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<>(new WebServlet(), urlMapping);
		configureH2ConsoleSettings(registration, properties.getSettings());
		dataSource.ifAvailable((available) -> {
			try (Connection connection = available.getConnection()) {
				logger.info("H2 console available at '" + path + "'. Database available at '"
						+ connection.getMetaData().getURL() + "'");
			}
			catch (Exception ex) {
				// Continue
			}
		});
		return registration;
	}

	private void configureH2ConsoleSettings(ServletRegistrationBean<WebServlet> registration, Settings settings) {
		if (settings.isTrace()) {
			registration.addInitParameter("trace", "");
		}
		if (settings.isWebAllowOthers()) {
			registration.addInitParameter("webAllowOthers", "");
		}
		if (settings.getWebAdminPassword() != null) {
			registration.addInitParameter("webAdminPassword", settings.getWebAdminPassword());
		}
	}

}
