package org.springframework.boot.convert;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DelimitedStringToArrayConverter}.
 *

 */
class DelimitedStringToArrayConverterTests {

	@ConversionServiceTest
	void canConvertFromStringToArrayShouldReturnTrue(ConversionService conversionService) {
		assertThat(conversionService.canConvert(String.class, String[].class)).isTrue();
	}

	@ConversionServiceTest
	void matchesWhenTargetIsNotAnnotatedShouldReturnTrue(ConversionService conversionService) {
		TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);
		TypeDescriptor targetType = TypeDescriptor.nested(ReflectionUtils.findField(Values.class, "noAnnotation"), 0);
		assertThat(new DelimitedStringToArrayConverter(conversionService).matches(sourceType, targetType)).isTrue();
	}

	@ConversionServiceTest
	void matchesWhenHasAnnotationAndNonConvertibleElementTypeShouldReturnFalse(ConversionService conversionService) {
		TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);
		TypeDescriptor targetType = TypeDescriptor
				.nested(ReflectionUtils.findField(Values.class, "nonConvertibleElementType"), 0);
		assertThat(new DelimitedStringToArrayConverter(conversionService).matches(sourceType, targetType)).isFalse();
	}

	@ConversionServiceTest
	void convertWhenHasDelimiterOfNoneShouldReturnWholeString(ConversionService conversionService) {
		TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);
		TypeDescriptor targetType = TypeDescriptor.nested(ReflectionUtils.findField(Values.class, "delimiterNone"), 0);
		String[] converted = (String[]) conversionService.convert("a,b,c", sourceType, targetType);
		assertThat(converted).containsExactly("a,b,c");
	}

	@Test
	void matchesWhenHasAnnotationAndConvertibleElementTypeShouldReturnTrue() {
		TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);
		TypeDescriptor targetType = TypeDescriptor
				.nested(ReflectionUtils.findField(Values.class, "convertibleElementType"), 0);
		assertThat(
				new DelimitedStringToArrayConverter(new ApplicationConversionService()).matches(sourceType, targetType))
						.isTrue();
	}

	@Test
	void convertWhenHasConvertibleElementTypeShouldReturnConvertedType() {
		TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);
		TypeDescriptor targetType = TypeDescriptor
				.nested(ReflectionUtils.findField(Values.class, "convertibleElementType"), 0);
		Integer[] converted = (Integer[]) new ApplicationConversionService().convert(" 1 |  2| 3  ", sourceType,
				targetType);
		assertThat(converted).containsExactly(1, 2, 3);
	}

	static Stream<? extends Arguments> conversionServices() {
		return ConversionServiceArguments
				.with((service) -> service.addConverter(new DelimitedStringToArrayConverter(service)));
	}

	static class Values {

		List<String> noAnnotation;

		@Delimiter("|")
		Integer[] convertibleElementType;

		@Delimiter("|")
		NonConvertible[] nonConvertibleElementType;

		@Delimiter(Delimiter.NONE)
		String[] delimiterNone;

	}

	static class NonConvertible {

	}

	static class MyCustomList<E> extends LinkedList<E> {

	}

}
