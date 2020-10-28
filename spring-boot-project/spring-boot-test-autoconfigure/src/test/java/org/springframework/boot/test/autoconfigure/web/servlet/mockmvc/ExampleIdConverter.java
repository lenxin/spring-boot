package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.util.UUID;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Example {@link Converter} used with {@link WebMvcTest @WebMvcTest} tests.
 *

 */
@Component
public class ExampleIdConverter implements Converter<String, ExampleId> {

	@Override
	public ExampleId convert(String source) {
		return new ExampleId(UUID.fromString(source));
	}

}
