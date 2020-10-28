package org.springframework.boot.web.servlet;

import javax.servlet.MultipartConfigElement;

import org.junit.jupiter.api.Test;

import org.springframework.util.unit.DataSize;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MultipartConfigFactory}.
 *


 */
class MultipartConfigFactoryTests {

	@Test
	void sensibleDefaults() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		MultipartConfigElement config = factory.createMultipartConfig();
		assertThat(config.getLocation()).isEqualTo("");
		assertThat(config.getMaxFileSize()).isEqualTo(-1L);
		assertThat(config.getMaxRequestSize()).isEqualTo(-1L);
		assertThat(config.getFileSizeThreshold()).isEqualTo(0);
	}

	@Test
	void createWithDataSizes() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(1));
		factory.setMaxRequestSize(DataSize.ofKilobytes(2));
		factory.setFileSizeThreshold(DataSize.ofMegabytes(3));
		MultipartConfigElement config = factory.createMultipartConfig();
		assertThat(config.getMaxFileSize()).isEqualTo(1L);
		assertThat(config.getMaxRequestSize()).isEqualTo(2 * 1024L);
		assertThat(config.getFileSizeThreshold()).isEqualTo(3 * 1024 * 1024);
	}

	@Test
	void createWithNegativeDataSizes() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(-1));
		factory.setMaxRequestSize(DataSize.ofKilobytes(-2));
		factory.setFileSizeThreshold(DataSize.ofMegabytes(-3));
		MultipartConfigElement config = factory.createMultipartConfig();
		assertThat(config.getMaxFileSize()).isEqualTo(-1L);
		assertThat(config.getMaxRequestSize()).isEqualTo(-1);
		assertThat(config.getFileSizeThreshold()).isEqualTo(0);
	}

}
