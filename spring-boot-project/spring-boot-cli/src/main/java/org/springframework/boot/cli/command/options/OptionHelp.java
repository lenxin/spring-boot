package org.springframework.boot.cli.command.options;

import java.util.Set;

/**
 * Help for a specific option.
 *

 * @since 1.0.0
 */
public interface OptionHelp {

	/**
	 * Returns the set of options that are mutually synonymous.
	 * @return the options
	 */
	Set<String> getOptions();

	/**
	 * Returns usage help for the option.
	 * @return the usage help
	 */
	String getUsageHelp();

}
