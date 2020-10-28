package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.util.List;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Example {@link WebMvcConfigurer} used in {@link WebMvcTest @WebMvcTest} tests.
 *

 */
@Component
public class ExampleWebMvcConfigurer implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new HandlerMethodArgumentResolver() {

			@Override
			public boolean supportsParameter(MethodParameter parameter) {
				return parameter.getParameterType().equals(ExampleArgument.class);
			}

			@Override
			public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
				return new ExampleArgument("hello");
			}

		});
	}

}
