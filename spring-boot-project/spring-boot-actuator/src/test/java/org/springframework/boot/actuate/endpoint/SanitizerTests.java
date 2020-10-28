package org.springframework.boot.actuate.endpoint;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Sanitizer}.
 *




 */
class SanitizerTests {

	@Test
	void defaultNonUriKeys() {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize("password", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("my-password", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("my-OTHER.paSSword", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("somesecret", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("somekey", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("token", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("sometoken", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("find", "secret")).isEqualTo("secret");
		assertThat(sanitizer.sanitize("sun.java.command", "--spring.redis.password=pa55w0rd")).isEqualTo("******");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriWithSingleValueWithPasswordShouldBeSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize(key, "http://user:password@localhost:8080"))
				.isEqualTo("http://user:******@localhost:8080");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriWithSingleValueWithNoPasswordShouldNotBeSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize(key, "http://localhost:8080")).isEqualTo("http://localhost:8080");
		assertThat(sanitizer.sanitize(key, "http://user@localhost:8080")).isEqualTo("http://user@localhost:8080");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriWithSingleValueWithPasswordMatchingOtherPartsOfStringShouldBeSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize(key, "http://user://@localhost:8080"))
				.isEqualTo("http://user:******@localhost:8080");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriWithMultipleValuesEachWithPasswordShouldHaveAllSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(
				sanitizer.sanitize(key, "http://user1:password1@localhost:8080,http://user2:password2@localhost:8082"))
						.isEqualTo("http://user1:******@localhost:8080,http://user2:******@localhost:8082");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriWithMultipleValuesNoneWithPasswordShouldHaveNoneSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize(key, "http://user@localhost:8080,http://localhost:8082"))
				.isEqualTo("http://user@localhost:8080,http://localhost:8082");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriWithMultipleValuesSomeWithPasswordShouldHaveThoseSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize(key,
				"http://user1:password1@localhost:8080,http://user2@localhost:8082,http://localhost:8083")).isEqualTo(
						"http://user1:******@localhost:8080,http://user2@localhost:8082,http://localhost:8083");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriWithMultipleValuesWithPasswordMatchingOtherPartsOfStringShouldBeSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize(key, "http://user1://@localhost:8080,http://user2://@localhost:8082"))
				.isEqualTo("http://user1:******@localhost:8080,http://user2:******@localhost:8082");
	}

	@ParameterizedTest(name = "key = {0}")
	@MethodSource("matchingUriUserInfoKeys")
	void uriKeyWithUserProvidedListLiteralShouldBeSanitized(String key) {
		Sanitizer sanitizer = new Sanitizer();
		assertThat(sanitizer.sanitize(key, "[amqp://username:password@host/]"))
				.isEqualTo("[amqp://username:******@host/]");
		assertThat(sanitizer.sanitize(key,
				"[http://user1:password1@localhost:8080,http://user2@localhost:8082,http://localhost:8083]")).isEqualTo(
						"[http://user1:******@localhost:8080,http://user2@localhost:8082,http://localhost:8083]");
		assertThat(sanitizer.sanitize(key,
				"[http://user1:password1@localhost:8080,http://user2:password2@localhost:8082]"))
						.isEqualTo("[http://user1:******@localhost:8080,http://user2:******@localhost:8082]");
		assertThat(sanitizer.sanitize(key, "[http://user1@localhost:8080,http://user2@localhost:8082]"))
				.isEqualTo("[http://user1@localhost:8080,http://user2@localhost:8082]");
	}

	private static Stream<String> matchingUriUserInfoKeys() {
		return Stream.of("uri", "my.uri", "myuri", "uris", "my.uris", "myuris", "address", "my.address", "myaddress",
				"addresses", "my.addresses", "myaddresses");
	}

	@Test
	void regex() {
		Sanitizer sanitizer = new Sanitizer(".*lock.*");
		assertThat(sanitizer.sanitize("verylOCkish", "secret")).isEqualTo("******");
		assertThat(sanitizer.sanitize("veryokish", "secret")).isEqualTo("secret");
	}

}
