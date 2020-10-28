package org.springframework.boot.actuate.autoconfigure.endpoint.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.expose.IncludeExcludeEndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.http.ActuatorMediaType;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoint;
import org.springframework.boot.actuate.endpoint.web.PathMapper;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpointDiscoverer;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebEndpointAutoConfiguration}.
 *



 */
class WebEndpointAutoConfigurationTests {

	private static final AutoConfigurations CONFIGURATIONS = AutoConfigurations.of(EndpointAutoConfiguration.class,
			WebEndpointAutoConfiguration.class);

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withConfiguration(CONFIGURATIONS);

	@Test
	void webApplicationConfiguresEndpointMediaTypes() {
		this.contextRunner.run((context) -> {
			EndpointMediaTypes endpointMediaTypes = context.getBean(EndpointMediaTypes.class);
			assertThat(endpointMediaTypes.getConsumed()).containsExactly(ActuatorMediaType.V3_JSON,
					ActuatorMediaType.V2_JSON, "application/json");
		});
	}

	@Test
	void webApplicationConfiguresPathMapper() {
		this.contextRunner.withPropertyValues("management.endpoints.web.path-mapping.health=healthcheck")
				.run((context) -> {
					assertThat(context).hasSingleBean(PathMapper.class);
					String pathMapping = context.getBean(PathMapper.class).getRootPath(EndpointId.of("health"));
					assertThat(pathMapping).isEqualTo("healthcheck");
				});
	}

	@Test
	void webApplicationSupportCustomPathMatcher() {
		this.contextRunner
				.withPropertyValues("management.endpoints.web.exposure.include=*",
						"management.endpoints.web.path-mapping.testanotherone=foo")
				.withUserConfiguration(TestPathMatcher.class, TestOneEndpoint.class, TestAnotherOneEndpoint.class,
						TestTwoEndpoint.class)
				.run((context) -> {
					WebEndpointDiscoverer discoverer = context.getBean(WebEndpointDiscoverer.class);
					Collection<ExposableWebEndpoint> endpoints = discoverer.getEndpoints();
					ExposableWebEndpoint[] webEndpoints = endpoints.toArray(new ExposableWebEndpoint[0]);
					List<String> paths = Arrays.stream(webEndpoints).map(PathMappedEndpoint::getRootPath)
							.collect(Collectors.toList());
					assertThat(paths).containsOnly("1/testone", "foo", "testtwo");
				});
	}

	@Test
	void webApplicationConfiguresEndpointDiscoverer() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasSingleBean(ControllerEndpointDiscoverer.class);
			assertThat(context).hasSingleBean(WebEndpointDiscoverer.class);
		});
	}

	@Test
	void webApplicationConfiguresExposeExcludePropertyEndpointFilter() {
		this.contextRunner
				.run((context) -> assertThat(context).getBeans(IncludeExcludeEndpointFilter.class).containsKeys(
						"webExposeExcludePropertyEndpointFilter", "controllerExposeExcludePropertyEndpointFilter"));
	}

	@Test
	void contextShouldConfigureServletEndpointDiscoverer() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(ServletEndpointDiscoverer.class));
	}

	@Test
	void contextWhenNotServletShouldNotConfigureServletEndpointDiscoverer() {
		new ApplicationContextRunner().withConfiguration(CONFIGURATIONS)
				.run((context) -> assertThat(context).doesNotHaveBean(ServletEndpointDiscoverer.class));
	}

	@Component
	static class TestPathMatcher implements PathMapper {

		@Override
		public String getRootPath(EndpointId endpointId) {
			if (endpointId.toString().endsWith("one")) {
				return "1/" + endpointId.toString();
			}
			return null;
		}

	}

	@Component
	@Endpoint(id = "testone")
	static class TestOneEndpoint {

	}

	@Component
	@Endpoint(id = "testanotherone")
	static class TestAnotherOneEndpoint {

	}

	@Component
	@Endpoint(id = "testtwo")
	static class TestTwoEndpoint {

	}

}
