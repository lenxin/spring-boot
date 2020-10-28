package org.springframework.boot.maven;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RunArguments}.
 *

 */
class RunArgumentsTests {

	@Test
	void parseNull() {
		String[] args = parseArgs(null);
		assertThat(args).isNotNull();
		assertThat(args).isEmpty();
	}

	@Test
	void parseNullArray() {
		String[] args = new RunArguments((String[]) null).asArray();
		assertThat(args).isNotNull();
		assertThat(args).isEmpty();
	}

	@Test
	void parseArrayContainingNullValue() {
		String[] args = new RunArguments(new String[] { "foo", null, "bar" }).asArray();
		assertThat(args).isNotNull();
		assertThat(args).containsOnly("foo", "bar");
	}

	@Test
	void parseArrayContainingEmptyValue() {
		String[] args = new RunArguments(new String[] { "foo", "", "bar" }).asArray();
		assertThat(args).isNotNull();
		assertThat(args).containsOnly("foo", "", "bar");
	}

	@Test
	void parseEmpty() {
		String[] args = parseArgs("   ");
		assertThat(args).isNotNull();
		assertThat(args).isEmpty();
	}

	@Test
	void parseDebugFlags() {
		String[] args = parseArgs("-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005");
		assertThat(args).hasSize(2);
		assertThat(args[0]).isEqualTo("-Xdebug");
		assertThat(args[1]).isEqualTo("-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005");
	}

	@Test
	void parseWithExtraSpaces() {
		String[] args = parseArgs("     -Dfoo=bar        -Dfoo2=bar2  ");
		assertThat(args).hasSize(2);
		assertThat(args[0]).isEqualTo("-Dfoo=bar");
		assertThat(args[1]).isEqualTo("-Dfoo2=bar2");
	}

	@Test
	void parseWithNewLinesAndTabs() {
		String[] args = parseArgs("     -Dfoo=bar \n\t\t -Dfoo2=bar2  ");
		assertThat(args).hasSize(2);
		assertThat(args[0]).isEqualTo("-Dfoo=bar");
		assertThat(args[1]).isEqualTo("-Dfoo2=bar2");
	}

	@Test
	void quoteHandledProperly() {
		String[] args = parseArgs("-Dvalue=\"My Value\"    ");
		assertThat(args).hasSize(1);
		assertThat(args[0]).isEqualTo("-Dvalue=My Value");
	}

	private String[] parseArgs(String args) {
		return new RunArguments(args).asArray();
	}

}
