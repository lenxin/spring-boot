package org.springframework.boot.buildpack.platform.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SharedObjectMapper}.
 *

 */
class SharedObjectMapperTests {

	@Test
	void getReturnsConfiguredObjectMapper() {
		ObjectMapper mapper = SharedObjectMapper.get();
		assertThat(mapper).isNotNull();
		assertThat(mapper.getRegisteredModuleIds()).contains(new ParameterNamesModule().getTypeId());
		assertThat(SerializationFeature.INDENT_OUTPUT
				.enabledIn(mapper.getSerializationConfig().getSerializationFeatures())).isTrue();
		assertThat(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
				.enabledIn(mapper.getDeserializationConfig().getDeserializationFeatures())).isFalse();
		assertThat(mapper.getSerializationConfig().getPropertyNamingStrategy())
				.isEqualTo(PropertyNamingStrategy.LOWER_CAMEL_CASE);
		assertThat(mapper.getDeserializationConfig().getPropertyNamingStrategy())
				.isEqualTo(PropertyNamingStrategy.LOWER_CAMEL_CASE);
	}

}
