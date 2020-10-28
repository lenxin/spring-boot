package org.springframework.boot.actuate.autoconfigure.metrics.export.elastic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ElasticPropertiesConfigAdapter}.
 *

 */
class ElasticPropertiesConfigAdapterTests {

	@Test
	void whenPropertiesHostsIsSetAdapterHostsReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setHost("https://elastic.example.com");
		assertThat(new ElasticPropertiesConfigAdapter(properties).host()).isEqualTo("https://elastic.example.com");
	}

	@Test
	void whenPropertiesIndexIsSetAdapterIndexReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setIndex("test-metrics");
		assertThat(new ElasticPropertiesConfigAdapter(properties).index()).isEqualTo("test-metrics");
	}

	@Test
	void whenPropertiesIndexDateFormatIsSetAdapterIndexDateFormatReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setIndexDateFormat("yyyy");
		assertThat(new ElasticPropertiesConfigAdapter(properties).indexDateFormat()).isEqualTo("yyyy");
	}

	@Test
	void whenPropertiesIndexDateSeparatorIsSetAdapterIndexDateSeparatorReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setIndexDateSeparator("*");
		assertThat(new ElasticPropertiesConfigAdapter(properties).indexDateSeparator()).isEqualTo("*");
	}

	@Test
	void whenPropertiesTimestampFieldNameIsSetAdapterTimestampFieldNameReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setTimestampFieldName("@test");
		assertThat(new ElasticPropertiesConfigAdapter(properties).timestampFieldName()).isEqualTo("@test");
	}

	@Test
	void whenPropertiesAutoCreateIndexIsSetAdapterAutoCreateIndexReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setAutoCreateIndex(false);
		assertThat(new ElasticPropertiesConfigAdapter(properties).autoCreateIndex()).isFalse();
	}

	@Test
	void whenPropertiesUserNameIsSetAdapterUserNameReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setUserName("alice");
		assertThat(new ElasticPropertiesConfigAdapter(properties).userName()).isEqualTo("alice");
	}

	@Test
	void whenPropertiesPasswordIsSetAdapterPasswordReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setPassword("secret");
		assertThat(new ElasticPropertiesConfigAdapter(properties).password()).isEqualTo("secret");
	}

	@Test
	void whenPropertiesPipelineIsSetAdapterPipelineReturnsIt() {
		ElasticProperties properties = new ElasticProperties();
		properties.setPipeline("testPipeline");
		assertThat(new ElasticPropertiesConfigAdapter(properties).pipeline()).isEqualTo("testPipeline");
	}

}
