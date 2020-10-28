package org.springframework.boot.autoconfigure.http;

import javax.json.bind.Jsonb;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Configuration for HTTP Message converters that use JSON-B.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Jsonb.class)
class JsonbHttpMessageConvertersConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnBean(Jsonb.class)
	@Conditional(PreferJsonbOrMissingJacksonAndGsonCondition.class)
	static class JsonbHttpMessageConverterConfiguration {

		@Bean
		@ConditionalOnMissingBean
		JsonbHttpMessageConverter jsonbHttpMessageConverter(Jsonb jsonb) {
			JsonbHttpMessageConverter converter = new JsonbHttpMessageConverter();
			converter.setJsonb(jsonb);
			return converter;
		}

	}

	private static class PreferJsonbOrMissingJacksonAndGsonCondition extends AnyNestedCondition {

		PreferJsonbOrMissingJacksonAndGsonCondition() {
			super(ConfigurationPhase.REGISTER_BEAN);
		}

		@ConditionalOnProperty(name = HttpMessageConvertersAutoConfiguration.PREFERRED_MAPPER_PROPERTY,
				havingValue = "jsonb")
		static class JsonbPreferred {

		}

		@ConditionalOnMissingBean({ MappingJackson2HttpMessageConverter.class, GsonHttpMessageConverter.class })
		static class JacksonAndGsonMissing {

		}

	}

}
