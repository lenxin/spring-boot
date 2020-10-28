package org.springframework.boot.autoconfigure.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;

/**
 * Callback interface that can be implemented by beans wishing to customize the
 * {@link CqlSession} via a {@link CqlSessionBuilder} whilst retaining default
 * auto-configuration.
 *

 * @since 2.3.0
 */
@FunctionalInterface
public interface CqlSessionBuilderCustomizer {

	/**
	 * Customize the {@link CqlSessionBuilder}.
	 * @param cqlSessionBuilder the builder to customize
	 */
	void customize(CqlSessionBuilder cqlSessionBuilder);

}
