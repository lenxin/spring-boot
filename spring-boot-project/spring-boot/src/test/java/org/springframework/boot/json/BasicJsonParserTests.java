package org.springframework.boot.json;

/**
 * Tests for {@link BasicJsonParser}.
 *

 */
public class BasicJsonParserTests extends AbstractJsonParserTests {

	@Override
	protected JsonParser getParser() {
		return new BasicJsonParser();
	}

}
