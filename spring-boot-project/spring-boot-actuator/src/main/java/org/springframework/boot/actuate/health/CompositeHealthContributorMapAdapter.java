package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;

/**
 * {@link CompositeHealthContributor} backed by a map with values adapted as necessary.
 *
 * @param <V> the value type

 */
class CompositeHealthContributorMapAdapter<V> extends NamedContributorsMapAdapter<V, HealthContributor>
		implements CompositeHealthContributor {

	CompositeHealthContributorMapAdapter(Map<String, V> map, Function<V, ? extends HealthContributor> valueAdapter) {
		super(map, valueAdapter);
	}

}
