package org.springframework.boot.build.context.properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CompoundConfigurationTableEntry}.
 *

 */
public class CompoundConfigurationTableEntryTests {

	private static final String NEWLINE = System.lineSeparator();

	@Test
	void simpleProperty() {
		ConfigurationProperty firstProp = new ConfigurationProperty("spring.test.first", "java.lang.String");
		ConfigurationProperty secondProp = new ConfigurationProperty("spring.test.second", "java.lang.String");
		ConfigurationProperty thirdProp = new ConfigurationProperty("spring.test.third", "java.lang.String");
		CompoundConfigurationTableEntry entry = new CompoundConfigurationTableEntry("spring.test",
				"This is a description.");
		entry.addConfigurationKeys(firstProp, secondProp, thirdProp);
		AsciidocBuilder builder = new AsciidocBuilder();
		entry.write(builder);
		assertThat(builder.toString()).isEqualTo("|[[spring.test]]<<spring.test,`+spring.test.first+` +" + NEWLINE
				+ "`+spring.test.second+` +" + NEWLINE + "`+spring.test.third+` +" + NEWLINE + ">>" + NEWLINE + NEWLINE
				+ "|" + NEWLINE + "|+++This is a description.+++" + NEWLINE);
	}

}
