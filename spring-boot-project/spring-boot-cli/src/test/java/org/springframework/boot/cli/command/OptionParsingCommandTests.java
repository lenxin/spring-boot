package org.springframework.boot.cli.command;

import org.junit.jupiter.api.Test;

import org.springframework.boot.cli.command.options.OptionHandler;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OptionParsingCommand}.
 *

 */
class OptionParsingCommandTests {

	@Test
	void optionHelp() {
		OptionHandler handler = new OptionHandler();
		handler.option("bar", "Bar");
		OptionParsingCommand command = new TestOptionParsingCommand("foo", "Foo", handler);
		assertThat(command.getHelp()).contains("--bar");
	}

	static class TestOptionParsingCommand extends OptionParsingCommand {

		TestOptionParsingCommand(String name, String description, OptionHandler handler) {
			super(name, description, handler);
		}

	}

}
