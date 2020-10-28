package org.springframework.boot.json;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.constructor.ConstructorException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link YamlJsonParser}.
 *

 */
public class YamlJsonParserTests extends AbstractJsonParserTests {

	@Override
	protected JsonParser getParser() {
		return new YamlJsonParser();
	}

	@Test
	void customTypesAreNotLoaded() throws Exception {
		assertThatExceptionOfType(ConstructorException.class)
				.isThrownBy(() -> getParser().parseMap("{value: !!java.net.URL [\"http://localhost:9000/\"]}"))
				.withCauseInstanceOf(IllegalStateException.class);
	}

}
