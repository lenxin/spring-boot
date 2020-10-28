package org.springframework.boot.convert;

import java.util.Collections;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link NumberToDataSizeConverter}.
 *

 */
class NumberToDataSizeConverterTests {

	@ConversionServiceTest
	void convertWhenSimpleWithoutSuffixShouldReturnDataSize(ConversionService conversionService) {
		assertThat(convert(conversionService, 10)).isEqualTo(DataSize.ofBytes(10));
		assertThat(convert(conversionService, +10)).isEqualTo(DataSize.ofBytes(10));
		assertThat(convert(conversionService, -10)).isEqualTo(DataSize.ofBytes(-10));
	}

	@ConversionServiceTest
	void convertWhenSimpleWithoutSuffixButWithAnnotationShouldReturnDataSize(ConversionService conversionService) {
		assertThat(convert(conversionService, 10, DataUnit.KILOBYTES)).isEqualTo(DataSize.ofKilobytes(10));
		assertThat(convert(conversionService, +10, DataUnit.KILOBYTES)).isEqualTo(DataSize.ofKilobytes(10));
		assertThat(convert(conversionService, -10, DataUnit.KILOBYTES)).isEqualTo(DataSize.ofKilobytes(-10));
	}

	private DataSize convert(ConversionService conversionService, Integer source) {
		return conversionService.convert(source, DataSize.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DataSize convert(ConversionService conversionService, Integer source, DataUnit defaultUnit) {
		TypeDescriptor targetType = mock(TypeDescriptor.class);
		if (defaultUnit != null) {
			DataSizeUnit unitAnnotation = AnnotationUtils
					.synthesizeAnnotation(Collections.singletonMap("value", defaultUnit), DataSizeUnit.class, null);
			given(targetType.getAnnotation(DataSizeUnit.class)).willReturn(unitAnnotation);
		}
		given(targetType.getType()).willReturn((Class) DataSize.class);
		return (DataSize) conversionService.convert(source, TypeDescriptor.forObject(source), targetType);
	}

	static Stream<? extends Arguments> conversionServices() {
		return ConversionServiceArguments.with(new NumberToDataSizeConverter());
	}

}
