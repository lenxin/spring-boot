package org.springframework.boot.build.context.properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConfigurationTable}.
 *

 */
public class ConfigurationTableTests {

	private static final String NEWLINE = System.lineSeparator();

	@Test
	void simpleTable() {
		ConfigurationTable table = new ConfigurationTable("test");
		ConfigurationProperty first = new ConfigurationProperty("spring.test.prop", "java.lang.String", "something",
				"This is a description.", false);
		ConfigurationProperty second = new ConfigurationProperty("spring.test.other", "java.lang.String", "other value",
				"This is another description.", false);
		table.addEntry(new SingleConfigurationTableEntry(first));
		table.addEntry(new SingleConfigurationTableEntry(second));
		assertThat(table.toAsciidocTable()).isEqualTo("[cols=\"2,1,1\", options=\"header\"]" + NEWLINE + "|==="
				+ NEWLINE + "|Key|Default Value|Description" + NEWLINE + NEWLINE
				+ "|[[spring.test.other]]<<spring.test.other,`+spring.test.other+`>>" + NEWLINE + "|`+other value+`"
				+ NEWLINE + "|+++This is another description.+++" + NEWLINE + NEWLINE
				+ "|[[spring.test.prop]]<<spring.test.prop,`+spring.test.prop+`>>" + NEWLINE + "|`+something+`"
				+ NEWLINE + "|+++This is a description.+++" + NEWLINE + NEWLINE + "|===" + NEWLINE);
	}

}
