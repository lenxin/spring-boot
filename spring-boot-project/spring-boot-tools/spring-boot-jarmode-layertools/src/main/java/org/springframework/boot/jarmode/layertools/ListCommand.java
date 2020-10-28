package org.springframework.boot.jarmode.layertools;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * The {@code 'list'} tools command.
 *

 */
class ListCommand extends Command {

	private Context context;

	ListCommand(Context context) {
		super("list", "List layers from the jar that can be extracted", Options.none(), Parameters.none());
		this.context = context;
	}

	@Override
	protected void run(Map<Option, String> options, List<String> parameters) {
		printLayers(Layers.get(this.context), System.out);
	}

	void printLayers(Layers layers, PrintStream out) {
		layers.forEach(out::println);
	}

}
