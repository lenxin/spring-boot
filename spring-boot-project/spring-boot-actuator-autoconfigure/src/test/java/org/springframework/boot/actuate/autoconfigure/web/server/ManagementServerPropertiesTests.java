package org.springframework.boot.actuate.autoconfigure.web.server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ManagementServerProperties}.
 *


 */
class ManagementServerPropertiesTests {

	@Test
	void defaultPortIsNull() {
		ManagementServerProperties properties = new ManagementServerProperties();
		assertThat(properties.getPort()).isNull();
	}

	@Test
	void definedPort() {
		ManagementServerProperties properties = new ManagementServerProperties();
		properties.setPort(123);
		assertThat(properties.getPort()).isEqualTo(123);
	}

	@Test
	@Deprecated
	void defaultContextPathIsEmptyString() {
		ManagementServerProperties properties = new ManagementServerProperties();
		assertThat(properties.getServlet().getContextPath()).isEqualTo("");
	}

	@Test
	@Deprecated
	void definedContextPath() {
		ManagementServerProperties properties = new ManagementServerProperties();
		properties.getServlet().setContextPath("/foo");
		assertThat(properties.getServlet().getContextPath()).isEqualTo("/foo");
	}

	@Test
	void defaultBasePathIsEmptyString() {
		ManagementServerProperties properties = new ManagementServerProperties();
		assertThat(properties.getBasePath()).isEqualTo("");
	}

	@Test
	void definedBasePath() {
		ManagementServerProperties properties = new ManagementServerProperties();
		properties.setBasePath("/foo");
		assertThat(properties.getBasePath()).isEqualTo("/foo");
	}

	@Test
	@Deprecated
	void trailingSlashOfContextPathIsRemoved() {
		ManagementServerProperties properties = new ManagementServerProperties();
		properties.getServlet().setContextPath("/foo/");
		assertThat(properties.getServlet().getContextPath()).isEqualTo("/foo");
	}

	@Test
	void trailingSlashOfBasePathIsRemoved() {
		ManagementServerProperties properties = new ManagementServerProperties();
		properties.setBasePath("/foo/");
		assertThat(properties.getBasePath()).isEqualTo("/foo");
	}

	@Test
	@Deprecated
	void slashOfContextPathIsDefaultValue() {
		ManagementServerProperties properties = new ManagementServerProperties();
		properties.getServlet().setContextPath("/");
		assertThat(properties.getServlet().getContextPath()).isEqualTo("");
	}

	@Test
	void slashOfBasePathIsDefaultValue() {
		ManagementServerProperties properties = new ManagementServerProperties();
		properties.setBasePath("/");
		assertThat(properties.getBasePath()).isEqualTo("");
	}

}
