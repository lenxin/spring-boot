package org.springframework.boot.convert;

import java.time.Period;
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
 * Tests for {@link NumberToPeriodConverter}.
 *


 */
class NumberToPeriodConverterTests {

	@ConversionServiceTest
	void convertWhenSimpleWithoutSuffixShouldReturnPeriod(ConversionService conversionService) {
		assertThat(convert(conversionService, 10)).hasDays(10);
		assertThat(convert(conversionService, +10)).hasDays(10);
		assertThat(convert(conversionService, -10)).hasDays(-10);
	}

	@ConversionServiceTest
	void convertWhenSimpleWithoutSuffixButWithAnnotationShouldReturnPeriod(ConversionService conversionService) {
		assertThat(convert(conversionService, 10, ChronoUnit.DAYS)).hasDays(10);
		assertThat(convert(conversionService, -10, ChronoUnit.DAYS)).hasDays(-10);
		assertThat(convert(conversionService, 10, ChronoUnit.WEEKS)).isEqualTo(Period.ofWeeks(10));
		assertThat(convert(conversionService, -10, ChronoUnit.WEEKS)).isEqualTo(Period.ofWeeks(-10));
		assertThat(convert(conversionService, 10, ChronoUnit.MONTHS)).hasMonths(10);
		assertThat(convert(conversionService, -10, ChronoUnit.MONTHS)).hasMonths(-10);
		assertThat(convert(conversionService, 10, ChronoUnit.YEARS)).hasYears(10);
		assertThat(convert(conversionService, -10, ChronoUnit.YEARS)).hasYears(-10);
	}

	private Period convert(ConversionService conversionService, Integer source) {
		return conversionService.convert(source, Period.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Period convert(ConversionService conversionService, Integer source, ChronoUnit defaultUnit) {
		TypeDescriptor targetType = mock(TypeDescriptor.class);
		if (defaultUnit != null) {
			PeriodUnit unitAnnotation = AnnotationUtils
					.synthesizeAnnotation(Collections.singletonMap("value", defaultUnit), PeriodUnit.class, null);
			given(targetType.getAnnotation(PeriodUnit.class)).willReturn(unitAnnotation);
		}
		given(targetType.getType()).willReturn((Class) Period.class);
		return (Period) conversionService.convert(source, TypeDescriptor.forObject(source), targetType);
	}

	static Stream<? extends Arguments> conversionServices() {
		return ConversionServiceArguments.with(new NumberToPeriodConverter());
	}

}
