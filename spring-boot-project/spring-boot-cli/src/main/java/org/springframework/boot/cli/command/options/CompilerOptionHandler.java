package org.springframework.boot.cli.command.options;

import java.util.Arrays;

import joptsimple.OptionSpec;

/**
 * An {@link OptionHandler} for commands that result in the compilation of one or more
 * Groovy scripts.
 *


 * @since 1.0.0
 */
public class CompilerOptionHandler extends OptionHandler {

	private OptionSpec<Void> noGuessImportsOption;

	private OptionSpec<Void> noGuessDependenciesOption;

	private OptionSpec<Boolean> autoconfigureOption;

	private OptionSpec<String> classpathOption;

	@Override
	protected final void options() {
		this.noGuessImportsOption = option("no-guess-imports", "Do not attempt to guess imports");
		this.noGuessDependenciesOption = option("no-guess-dependencies", "Do not attempt to guess dependencies");
		this.autoconfigureOption = option("autoconfigure", "Add autoconfigure compiler transformations")
				.withOptionalArg().ofType(Boolean.class).defaultsTo(true);
		this.classpathOption = option(Arrays.asList("classpath", "cp"), "Additional classpath entries")
				.withRequiredArg();
		doOptions();
	}

	protected void doOptions() {
	}

	public OptionSpec<Void> getNoGuessImportsOption() {
		return this.noGuessImportsOption;
	}

	public OptionSpec<Void> getNoGuessDependenciesOption() {
		return this.noGuessDependenciesOption;
	}

	public OptionSpec<String> getClasspathOption() {
		return this.classpathOption;
	}

	public OptionSpec<Boolean> getAutoconfigureOption() {
		return this.autoconfigureOption;
	}

}
