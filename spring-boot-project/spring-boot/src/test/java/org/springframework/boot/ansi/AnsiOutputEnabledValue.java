package org.springframework.boot.ansi;

import org.springframework.boot.ansi.AnsiOutput.Enabled;

/**
 * Public access to {@link AnsiOutput#getEnabled()} for other tests to use.
 *

 */
public final class AnsiOutputEnabledValue {

	private AnsiOutputEnabledValue() {
	}

	public static Enabled get() {
		return AnsiOutput.getEnabled();
	}

}
