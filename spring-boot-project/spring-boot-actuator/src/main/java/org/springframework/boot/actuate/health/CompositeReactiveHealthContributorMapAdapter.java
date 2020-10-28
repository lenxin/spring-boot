package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;

/**
 * {@link CompositeReactiveHealthContributor} backed by a map with values adapted as
 * necessary.
 *
 * @param <V> the value type

 */
class CompositeReactiveHealthContributorMapAdapter<V> extends NamedContributorsMapAdapter<V, ReactiveHealthContributor>
		implements CompositeReactiveHealthContributor {

	CompositeReactiveHealthContributorMapAdapter(Map<String, V> map,
			Function<V, ? extends ReactiveHealthContributor> valueAdapter) {
		super(map, valueAdapter);
	}

}
