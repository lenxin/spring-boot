package org.springframework.boot.buildpack.platform.docker;

import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * Utility to render a simple progress bar based on consumed {@link TotalProgressEvent}
 * objects.
 *

 * @since 2.3.0
 */
public class TotalProgressBar implements Consumer<TotalProgressEvent> {

	private final char progressChar;

	private final boolean bookend;

	private final PrintStream out;

	private int printed;

	/**
	 * Create a new {@link TotalProgressBar} instance.
	 * @param prefix the prefix to output
	 */
	public TotalProgressBar(String prefix) {
		this(prefix, System.out);
	}

	/**
	 * Create a new {@link TotalProgressBar} instance.
	 * @param prefix the prefix to output
	 * @param out the output print stream to use
	 */
	public TotalProgressBar(String prefix, PrintStream out) {
		this(prefix, '#', true, out);
	}

	/**
	 * Create a new {@link TotalProgressBar} instance.
	 * @param prefix the prefix to output
	 * @param progressChar the progress char to print
	 * @param bookend if bookends should be printed
	 * @param out the output print stream to use
	 */
	public TotalProgressBar(String prefix, char progressChar, boolean bookend, PrintStream out) {
		this.progressChar = progressChar;
		this.bookend = bookend;
		if (prefix != null && !prefix.isEmpty()) {
			out.print(prefix);
			out.print(" ");
		}
		if (bookend) {
			out.print("[ ");
		}
		this.out = out;
	}

	@Override
	public void accept(TotalProgressEvent event) {
		int percent = event.getPercent() / 2;
		while (this.printed < percent) {
			this.out.print(this.progressChar);
			this.printed++;
		}
		if (event.getPercent() == 100) {
			this.out.println(this.bookend ? " ]" : "");
		}
	}

}
