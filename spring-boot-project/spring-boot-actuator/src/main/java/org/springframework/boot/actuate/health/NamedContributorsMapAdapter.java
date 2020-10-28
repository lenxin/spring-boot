package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.util.Assert;

/**
 * {@link NamedContributors} backed by a map with values adapted as necessary.
 *
 * @param <V> the value type
 * @param <C> the contributor type

 * @see CompositeHealthContributorMapAdapter
 * @see CompositeReactiveHealthContributorMapAdapter
 */
abstract class NamedContributorsMapAdapter<V, C> implements NamedContributors<C> {

	private final Map<String, V> map;

	private final Function<V, ? extends C> valueAdapter;

	NamedContributorsMapAdapter(Map<String, V> map, Function<V, ? extends C> valueAdapter) {
		Assert.notNull(map, "Map must not be null");
		Assert.notNull(valueAdapter, "ValueAdapter must not be null");
		map.keySet().forEach((key) -> Assert.notNull(key, "Map must not contain null keys"));
		map.values().stream().map(valueAdapter)
				.forEach((value) -> Assert.notNull(value, "Map must not contain null values"));
		this.map = Collections.unmodifiableMap(new LinkedHashMap<>(map));
		this.valueAdapter = valueAdapter;
	}

	@Override
	public Iterator<NamedContributor<C>> iterator() {
		Iterator<Entry<String, V>> iterator = this.map.entrySet().iterator();
		return new Iterator<NamedContributor<C>>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public NamedContributor<C> next() {
				Entry<String, V> entry = iterator.next();
				return NamedContributor.of(entry.getKey(), adapt(entry.getValue()));
			}

		};
	}

	@Override
	public C getContributor(String name) {
		return adapt(this.map.get(name));
	}

	private C adapt(V value) {
		return (value != null) ? this.valueAdapter.apply(value) : null;
	}

}
