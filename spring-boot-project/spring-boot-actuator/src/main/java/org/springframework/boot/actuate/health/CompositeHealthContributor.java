package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;

/**
 * A {@link HealthContributor} that is composed of other {@link HealthContributor}
 * instances.
 *

 * @since 2.2.0
 * @see CompositeHealth
 * @see CompositeReactiveHealthContributor
 */
public interface CompositeHealthContributor extends HealthContributor, NamedContributors<HealthContributor> {

	/**
	 * Factory method that will create a {@link CompositeHealthContributor} from the
	 * specified map.
	 * @param map the source map
	 * @return a composite health contributor instance
	 */
	static CompositeHealthContributor fromMap(Map<String, ? extends HealthContributor> map) {
		return fromMap(map, Function.identity());
	}

	/**
	 * Factory method that will create a {@link CompositeHealthContributor} from the
	 * specified map.
	 * @param <V> the value type
	 * @param map the source map
	 * @param valueAdapter function used to adapt the map value
	 * @return a composite health contributor instance
	 */
	static <V> CompositeHealthContributor fromMap(Map<String, V> map,
			Function<V, ? extends HealthContributor> valueAdapter) {
		return new CompositeHealthContributorMapAdapter<>(map, valueAdapter);
	}

}
