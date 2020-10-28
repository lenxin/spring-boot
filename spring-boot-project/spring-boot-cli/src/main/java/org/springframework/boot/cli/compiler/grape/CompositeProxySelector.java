package org.springframework.boot.cli.compiler.grape;

import java.util.List;

import org.eclipse.aether.repository.Proxy;
import org.eclipse.aether.repository.ProxySelector;
import org.eclipse.aether.repository.RemoteRepository;

/**
 * Composite {@link ProxySelector}.
 *

 * @since 1.1.0
 */
public class CompositeProxySelector implements ProxySelector {

	private final List<ProxySelector> selectors;

	public CompositeProxySelector(List<ProxySelector> selectors) {
		this.selectors = selectors;
	}

	@Override
	public Proxy getProxy(RemoteRepository repository) {
		for (ProxySelector selector : this.selectors) {
			Proxy proxy = selector.getProxy(repository);
			if (proxy != null) {
				return proxy;
			}
		}
		return null;
	}

}
