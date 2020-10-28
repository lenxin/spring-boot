package org.springframework.boot.jarmode.layertools;

import java.util.Iterator;
import java.util.zip.ZipEntry;

/**
 * Provides information about the jar layers.
 *

 * @see ExtractCommand
 * @see ListCommand
 */
interface Layers extends Iterable<String> {

	/**
	 * Return the jar layers in the order that they should be added (starting with the
	 * least frequently changed layer).
	 */
	@Override
	Iterator<String> iterator();

	/**
	 * Return the layer that a given entry is in.
	 * @param entry the entry to check
	 * @return the layer that the entry is in
	 */
	String getLayer(ZipEntry entry);

	/**
	 * Return a {@link Layers} instance for the currently running application.
	 * @param context the command context
	 * @return a new layers instance
	 */
	static Layers get(Context context) {
		IndexedLayers indexedLayers = IndexedLayers.get(context);
		if (indexedLayers == null) {
			throw new IllegalStateException("Failed to load layers.idx which is required by layertools");
		}
		return indexedLayers;
	}

}
