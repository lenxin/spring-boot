package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.cache.CachesEndpoint;
import org.springframework.boot.actuate.cache.CachesEndpointWebExtension;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing the {@link CachesEndpoint}
 *

 */
class CachesEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	private static final List<FieldDescriptor> levelFields = Arrays.asList(
			fieldWithPath("name").description("Cache name."),
			fieldWithPath("cacheManager").description("Cache manager name."),
			fieldWithPath("target").description("Fully qualified name of the native cache."));

	private static final List<ParameterDescriptor> requestParameters = Collections
			.singletonList(parameterWithName("cacheManager").description(
					"Name of the cacheManager to qualify the cache. May be omitted if the cache name is unique.")
					.optional());

	@Test
	void allCaches() throws Exception {
		this.mockMvc.perform(get("/actuator/caches")).andExpect(status().isOk())
				.andDo(MockMvcRestDocumentation.document("caches/all",
						responseFields(fieldWithPath("cacheManagers").description("Cache managers keyed by id."),
								fieldWithPath("cacheManagers.*.caches")
										.description("Caches in the application context keyed by name."))
												.andWithPrefix("cacheManagers.*.caches.*.", fieldWithPath("target")
														.description("Fully qualified name of the native cache."))));
	}

	@Test
	void namedCache() throws Exception {
		this.mockMvc.perform(get("/actuator/caches/cities")).andExpect(status().isOk()).andDo(MockMvcRestDocumentation
				.document("caches/named", requestParameters(requestParameters), responseFields(levelFields)));
	}

	@Test
	void evictAllCaches() throws Exception {
		this.mockMvc.perform(delete("/actuator/caches")).andExpect(status().isNoContent())
				.andDo(MockMvcRestDocumentation.document("caches/evict-all"));
	}

	@Test
	void evictNamedCache() throws Exception {
		this.mockMvc.perform(delete("/actuator/caches/countries?cacheManager=anotherCacheManager"))
				.andExpect(status().isNoContent())
				.andDo(MockMvcRestDocumentation.document("caches/evict-named", requestParameters(requestParameters)));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		CachesEndpoint endpoint() {
			Map<String, CacheManager> cacheManagers = new HashMap<>();
			cacheManagers.put("cacheManager", new ConcurrentMapCacheManager("countries", "cities"));
			cacheManagers.put("anotherCacheManager", new ConcurrentMapCacheManager("countries"));
			return new CachesEndpoint(cacheManagers);
		}

		@Bean
		CachesEndpointWebExtension endpointWebExtension(CachesEndpoint endpoint) {
			return new CachesEndpointWebExtension(endpoint);
		}

	}

}
