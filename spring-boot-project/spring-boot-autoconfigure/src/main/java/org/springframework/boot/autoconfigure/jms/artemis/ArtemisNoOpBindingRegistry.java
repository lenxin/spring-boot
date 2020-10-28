package org.springframework.boot.autoconfigure.jms.artemis;

import org.apache.activemq.artemis.spi.core.naming.BindingRegistry;

/**
 * A no-op implementation of the {@link BindingRegistry}.
 *


 * @since 1.3.0
 */
public class ArtemisNoOpBindingRegistry implements BindingRegistry {

	@Override
	public Object lookup(String s) {
		return null;
	}

	@Override
	public boolean bind(String s, Object o) {
		return false;
	}

	@Override
	public void unbind(String s) {
	}

	@Override
	public void close() {
	}

}
