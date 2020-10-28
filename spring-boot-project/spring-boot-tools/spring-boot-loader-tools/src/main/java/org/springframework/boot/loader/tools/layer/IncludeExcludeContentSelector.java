package org.springframework.boot.loader.tools.layer;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.loader.tools.Layer;
import org.springframework.util.Assert;

/**
 * {@link ContentSelector} backed by {@code include}/{@code exclude} {@link ContentFilter
 * filters}.
 *
 * @param <T> the content type


 * @since 2.3.0
 */
public class IncludeExcludeContentSelector<T> implements ContentSelector<T> {

	private final Layer layer;

	private final List<ContentFilter<T>> includes;

	private final List<ContentFilter<T>> excludes;

	public IncludeExcludeContentSelector(Layer layer, List<ContentFilter<T>> includes,
			List<ContentFilter<T>> excludes) {
		this(layer, includes, excludes, Function.identity());
	}

	public <S> IncludeExcludeContentSelector(Layer layer, List<S> includes, List<S> excludes,
			Function<S, ContentFilter<T>> filterFactory) {
		Assert.notNull(layer, "Layer must not be null");
		Assert.notNull(filterFactory, "FilterFactory must not be null");
		this.layer = layer;
		this.includes = (includes != null) ? adapt(includes, filterFactory) : Collections.emptyList();
		this.excludes = (excludes != null) ? adapt(excludes, filterFactory) : Collections.emptyList();
	}

	private <S> List<ContentFilter<T>> adapt(List<S> list, Function<S, ContentFilter<T>> mapper) {
		return list.stream().map(mapper).collect(Collectors.toList());
	}

	@Override
	public Layer getLayer() {
		return this.layer;
	}

	@Override
	public boolean contains(T item) {
		return isIncluded(item) && !isExcluded(item);
	}

	private boolean isIncluded(T item) {
		if (this.includes.isEmpty()) {
			return true;
		}
		for (ContentFilter<T> include : this.includes) {
			if (include.matches(item)) {
				return true;
			}
		}
		return false;
	}

	private boolean isExcluded(T item) {
		if (this.excludes.isEmpty()) {
			return false;
		}
		for (ContentFilter<T> exclude : this.excludes) {
			if (exclude.matches(item)) {
				return true;
			}
		}
		return false;
	}

}
