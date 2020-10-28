package org.springframework.boot.convert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link NumberToDurationConverter}.
 *

 */
class NumberToDurationConverterTests {

	@ConversionServiceTest
	void convertWhenSimpleWithoutSuffixShouldReturnDuration(ConversionService conversionService) {
		assertThat(convert(conversionService, 10)).hasMillis(10);
		assertThat(convert(conversionService, +10)).hasMillis(10);
		assertThat(convert(conversionService, -10)).hasMillis(-10);
	}

	@ConversionServiceTest
	void convertWhenSimpleWithoutSuffixButWithAnnotationShouldReturnDuration(ConversionService conversionService) {
		assertThat(convert(conversionService, 10, ChronoUnit.SECONDS)).hasSeconds(10);
		assertThat(convert(conversionService, +10, ChronoUnit.SECONDS)).hasSeconds(10);
		assertThat(convert(conversionService, -10, ChronoUnit.SECONDS)).hasSeconds(-10);
	}

	private Duration convert(ConversionService conversionService, Integer source) {
		return conversionService.convert(source, Duration.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Duration convert(ConversionService conversionService, Integer source, ChronoUnit defaultUnit) {
		TypeDescriptor targetType = mock(TypeDescriptor.class);
		if (defaultUnit != null) {
			DurationUnit unitAnnotation = AnnotationUtils
					.synthesizeAnnotation(Collections.singletonMap("value", defaultUnit), DurationUnit.class, null);
			given(targetType.getAnnotation(DurationUnit.class)).willReturn(unitAnnotation);
		}
		given(targetType.getType()).willReturn((Class) Duration.class);
		return (Duration) conversionService.convert(source, TypeDescriptor.forObject(source), targetType);
	}

	static Stream<? extends Arguments> conversionServices() {
		return ConversionServiceArguments.with(new NumberToDurationConverter());
	}

}
