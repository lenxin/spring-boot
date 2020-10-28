package org.springframework.boot.actuate.endpoint.web.annotation;

import java.lang.reflect.Method;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.Selector.Match;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.core.annotation.AnnotationAttributes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link RequestPredicateFactory}.
 *

 */
class RequestPredicateFactoryTests {

	private final RequestPredicateFactory factory = new RequestPredicateFactory(
			new EndpointMediaTypes(Collections.emptyList(), Collections.emptyList()));

	private String rootPath = "/root";

	@Test
	void getRequestPredicateWhenHasMoreThanOneMatchAllThrowsException() {
		DiscoveredOperationMethod operationMethod = getDiscoveredOperationMethod(MoreThanOneMatchAll.class);
		assertThatIllegalStateException()
				.isThrownBy(() -> this.factory.getRequestPredicate(this.rootPath, operationMethod))
				.withMessage("@Selector annotation with Match.ALL_REMAINING must be unique");
	}

	@Test
	void getRequestPredicateWhenMatchAllIsNotLastParameterThrowsException() {
		DiscoveredOperationMethod operationMethod = getDiscoveredOperationMethod(MatchAllIsNotLastParameter.class);
		assertThatIllegalStateException()
				.isThrownBy(() -> this.factory.getRequestPredicate(this.rootPath, operationMethod))
				.withMessage("@Selector annotation with Match.ALL_REMAINING must be the last parameter");
	}

	@Test
	void getRequestPredicateReturnsPredicateWithPath() {
		DiscoveredOperationMethod operationMethod = getDiscoveredOperationMethod(ValidSelectors.class);
		WebOperationRequestPredicate requestPredicate = this.factory.getRequestPredicate(this.rootPath,
				operationMethod);
		assertThat(requestPredicate.getPath()).isEqualTo("/root/{one}/{*two}");
	}

	private DiscoveredOperationMethod getDiscoveredOperationMethod(Class<?> source) {
		Method method = source.getDeclaredMethods()[0];
		AnnotationAttributes attributes = new AnnotationAttributes();
		attributes.put("produces", "application/json");
		return new DiscoveredOperationMethod(method, OperationType.READ, attributes);
	}

	static class MoreThanOneMatchAll {

		void test(@Selector(match = Match.ALL_REMAINING) String[] one,
				@Selector(match = Match.ALL_REMAINING) String[] two) {
		}

	}

	static class MatchAllIsNotLastParameter {

		void test(@Selector(match = Match.ALL_REMAINING) String[] one, @Selector String[] two) {
		}

	}

	static class ValidSelectors {

		void test(@Selector String[] one, @Selector(match = Match.ALL_REMAINING) String[] two) {
		}

	}

}
