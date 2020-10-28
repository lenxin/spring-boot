package org.springframework.boot.actuate.health;

import java.util.Map;
import java.util.function.Function;

/**
 * A {@link ReactiveHealthContributor} that is composed of other
 * {@link ReactiveHealthContributor} instances.
 *

 * @since 2.2.0
 * @see CompositeHealth
 * @see CompositeHealthContributor
 */
public interface CompositeReactiveHealthContributor
		extends ReactiveHealthContributor, NamedContributors<ReactiveHealthContributor> {

	/**
	 * Factory method that will create a {@link CompositeReactiveHealthContributor} from
	 * the specified map.
	 * @param map the source map
	 * @return a composite health contributor instance
	 */
	static CompositeReactiveHealthContributor fromMap(Map<String, ? extends ReactiveHealthContributor> map) {
		return fromMap(map, Function.identity());
	}

	/**
	 * Factory method that will create a {@link CompositeReactiveHealthContributor} from
	 * the specified map.
	 * @param <V> the value type
	 * @param map the source map
	 * @param valueAdapter function used to adapt the map value
	 * @return a composite health contributor instance
	 */
	static <V> CompositeReactiveHealthContributor fromMap(Map<String, V> map,
			Function<V, ? extends ReactiveHealthContributor> valueAdapter) {
		return new CompositeReactiveHealthContributorMapAdapter<>(map, valueAdapter);
	}

}
