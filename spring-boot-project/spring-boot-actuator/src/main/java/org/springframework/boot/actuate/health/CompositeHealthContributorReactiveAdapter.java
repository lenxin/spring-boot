package org.springframework.boot.actuate.health;

import java.util.Iterator;

import org.springframework.util.Assert;

/**
 * Adapts a {@link CompositeHealthContributor} to a
 * {@link CompositeReactiveHealthContributor} so that it can be safely invoked in a
 * reactive environment.
 *

 * @see ReactiveHealthContributor#adapt(HealthContributor)
 */
class CompositeHealthContributorReactiveAdapter implements CompositeReactiveHealthContributor {

	private final CompositeHealthContributor delegate;

	CompositeHealthContributorReactiveAdapter(CompositeHealthContributor delegate) {
		Assert.notNull(delegate, "Delegate must not be null");
		this.delegate = delegate;
	}

	@Override
	public Iterator<NamedContributor<ReactiveHealthContributor>> iterator() {
		Iterator<NamedContributor<HealthContributor>> iterator = this.delegate.iterator();
		return new Iterator<NamedContributor<ReactiveHealthContributor>>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public NamedContributor<ReactiveHealthContributor> next() {
				NamedContributor<HealthContributor> namedContributor = iterator.next();
				return NamedContributor.of(namedContributor.getName(),
						ReactiveHealthContributor.adapt(namedContributor.getContributor()));
			}

		};
	}

	@Override
	public ReactiveHealthContributor getContributor(String name) {
		HealthContributor contributor = this.delegate.getContributor(name);
		return (contributor != null) ? ReactiveHealthContributor.adapt(contributor) : null;
	}

}
