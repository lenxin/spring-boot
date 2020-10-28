package org.springframework.boot.convert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginProvider;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/**
 * Tests for {@link InputStreamSourceToByteArrayConverter}.
 *

 */
class InputStreamSourceToByteArrayConverterTests {

	@ConversionServiceTest
	void convertConvertsSource(ConversionService conversionService) {
		InputStreamSource source = () -> new ByteArrayInputStream(new byte[] { 0, 1, 2 });
		assertThat(conversionService.convert(source, byte[].class)).containsExactly(0, 1, 2);
	}

	@ConversionServiceTest
	void convertWhenFailsWithIOExceptionThrowsException(ConversionService conversionService) throws Exception {
		InputStreamSource source = mock(InputStreamSource.class);
		given(source.getInputStream()).willThrow(IOException.class);
		assertThatExceptionOfType(ConversionFailedException.class)
				.isThrownBy(() -> conversionService.convert(source, byte[].class))
				.withCauseExactlyInstanceOf(IllegalStateException.class)
				.withMessageContaining("Unable to read from input stream source");
	}

	@ConversionServiceTest
	void convertWhenFailsWithIOExceptionFromOriginProviderThrowsException(ConversionService conversionService)
			throws Exception {
		Origin origin = new TestOrigin("mylocation");
		InputStreamSource source = mock(InputStreamSource.class, withSettings().extraInterfaces(OriginProvider.class));
		given(source.getInputStream()).willThrow(IOException.class);
		given(((OriginProvider) source).getOrigin()).willReturn(origin);
		assertThatExceptionOfType(ConversionFailedException.class)
				.isThrownBy(() -> conversionService.convert(source, byte[].class))
				.withCauseExactlyInstanceOf(IllegalStateException.class)
				.withMessageContaining("Unable to read from mylocation");
	}

	@ConversionServiceTest
	void convertWhenFailsWithIOExceptionFromResourceThrowsException(ConversionService conversionService)
			throws Exception {
		Resource source = mock(Resource.class);
		given(source.getInputStream()).willThrow(IOException.class);
		given(source.getDescription()).willReturn("myresource");
		assertThatExceptionOfType(ConversionFailedException.class)
				.isThrownBy(() -> conversionService.convert(source, byte[].class))
				.withCauseExactlyInstanceOf(IllegalStateException.class)
				.withMessageContaining("Unable to read from myresource");
	}

	static Stream<? extends Arguments> conversionServices() {
		return ConversionServiceArguments
				.with((service) -> service.addConverter(new InputStreamSourceToByteArrayConverter()));
	}

	private static class TestOrigin implements Origin {

		private final String string;

		TestOrigin(String string) {
			this.string = string;
		}

		@Override
		public String toString() {
			return this.string;
		}

	}

}
