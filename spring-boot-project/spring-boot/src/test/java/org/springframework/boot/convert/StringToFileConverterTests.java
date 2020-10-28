package org.springframework.boot.convert;

import java.io.File;
import java.util.stream.Stream;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.provider.Arguments;

import org.springframework.core.convert.ConversionService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link StringToFileConverter}.
 *

 */
class StringToFileConverterTests {

	@TempDir
	File temp;

	@ConversionServiceTest
	void convertWhenSimpleFileReturnsFile(ConversionService conversionService) {
		assertThat(convert(conversionService, this.temp.getAbsolutePath() + "/test"))
				.isEqualTo(new File(this.temp, "test").getAbsoluteFile());
	}

	@ConversionServiceTest
	void convertWhenFilePrefixedReturnsFile(ConversionService conversionService) {
		assertThat(convert(conversionService, "file:" + this.temp.getAbsolutePath() + "/test").getAbsoluteFile())
				.isEqualTo(new File(this.temp, "test").getAbsoluteFile());
	}

	private File convert(ConversionService conversionService, String source) {
		return conversionService.convert(source, File.class);
	}

	static Stream<? extends Arguments> conversionServices() {
		return ConversionServiceArguments
				.with((conversionService) -> conversionService.addConverter(new StringToFileConverter()));
	}

}
