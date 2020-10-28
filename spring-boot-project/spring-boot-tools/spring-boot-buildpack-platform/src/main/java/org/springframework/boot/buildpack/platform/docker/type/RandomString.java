package org.springframework.boot.buildpack.platform.docker.type;

import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.util.Assert;

/**
 * Utility class used to generate random strings.
 *

 */
final class RandomString {

	private static final Random random = new Random();

	private RandomString() {
	}

	static String generate(String prefix, int randomLength) {
		Assert.notNull(prefix, "Prefix must not be null");
		return prefix + generateRandom(randomLength);
	}

	static CharSequence generateRandom(int length) {
		IntStream chars = random.ints('a', 'z' + 1).limit(length);
		return chars.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
	}

}
