package org.springframework.boot.convert;

import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionService;

/**
 * Factory for creating a {@link Stream stream} of {@link Arguments} for use in a
 * {@link ParameterizedTest parameterized test}.
 *


 */
final class ConversionServiceArguments {

	private ConversionServiceArguments() {
	}

	static Stream<? extends Arguments> with(Formatter<?> formatter) {
		return with((conversionService) -> conversionService.addFormatter(formatter));
	}

	static Stream<? extends Arguments> with(GenericConverter converter) {
		return with((conversionService) -> conversionService.addConverter(converter));
	}

	static Stream<? extends Arguments> with(Consumer<FormattingConversionService> initializer) {
		FormattingConversionService withoutDefaults = new FormattingConversionService();
		initializer.accept(withoutDefaults);
		return Stream.of(
				Arguments.of(new NamedConversionService(withoutDefaults, "Without defaults conversion service")),
				Arguments.of(new NamedConversionService(new ApplicationConversionService(),
						"Application conversion service")));
	}

	static class NamedConversionService implements ConversionService {

		private final ConversionService delegate;

		private final String name;

		NamedConversionService(ConversionService delegate, String name) {
			this.delegate = delegate;
			this.name = name;
		}

		@Override
		public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
			return this.delegate.canConvert(sourceType, targetType);
		}

		@Override
		public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
			return this.delegate.canConvert(sourceType, targetType);
		}

		@Override
		public <T> T convert(Object source, Class<T> targetType) {
			return this.delegate.convert(source, targetType);
		}

		@Override
		public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
			return this.delegate.convert(source, sourceType, targetType);
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

}
