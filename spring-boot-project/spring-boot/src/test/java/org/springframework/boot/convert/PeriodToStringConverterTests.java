package org.springframework.boot.convert;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PeriodToStringConverter}.
 *


 */
class PeriodToStringConverterTests {

	@ConversionServiceTest
	void convertWithoutStyleShouldReturnIso8601(ConversionService conversionService) {
		String converted = conversionService.convert(Period.ofDays(1), String.class);
		assertThat(converted).isEqualTo(Period.ofDays(1).toString());
	}

	@ConversionServiceTest
	void convertWithFormatWhenZeroShouldUseFormatAndDays(ConversionService conversionService) {
		String converted = (String) conversionService.convert(Period.ofMonths(0),
				MockPeriodTypeDescriptor.get(null, PeriodStyle.SIMPLE), TypeDescriptor.valueOf(String.class));
		assertThat(converted).isEqualTo("0d");
	}

	@ConversionServiceTest
	void convertWithFormatShouldUseFormat(ConversionService conversionService) {
		String converted = (String) conversionService.convert(Period.of(1, 2, 3),
				MockPeriodTypeDescriptor.get(null, PeriodStyle.SIMPLE), TypeDescriptor.valueOf(String.class));
		assertThat(converted).isEqualTo("1y2m3d");
	}

	@ConversionServiceTest
	void convertWithFormatAndUnitWhenZeroShouldUseFormatAndUnit(ConversionService conversionService) {
		String converted = (String) conversionService.convert(Period.ofYears(0),
				MockPeriodTypeDescriptor.get(ChronoUnit.YEARS, PeriodStyle.SIMPLE),
				TypeDescriptor.valueOf(String.class));
		assertThat(converted).isEqualTo("0y");
	}

	@ConversionServiceTest
	void convertWithFormatAndUnitWhenNonZeroShouldUseFormatAndIgnoreUnit(ConversionService conversionService) {
		String converted = (String) conversionService.convert(Period.of(1, 0, 3),
				MockPeriodTypeDescriptor.get(ChronoUnit.YEARS, PeriodStyle.SIMPLE),
				TypeDescriptor.valueOf(String.class));
		assertThat(converted).isEqualTo("1y3d");
	}

	@ConversionServiceTest
	void convertWithWeekUnitShouldConvertToStringInDays(ConversionService conversionService) {
		String converted = (String) conversionService.convert(Period.ofWeeks(53),
				MockPeriodTypeDescriptor.get(null, PeriodStyle.SIMPLE), TypeDescriptor.valueOf(String.class));
		assertThat(converted).isEqualTo("371d");
	}

	static Stream<? extends Arguments> conversionServices() throws Exception {
		return ConversionServiceArguments.with(new PeriodToStringConverter());
	}

}
