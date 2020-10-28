package org.springframework.boot.test.context;

import java.util.Set;

import org.springframework.test.context.TestExecutionListener;

/**
 * Callback interface trigger from {@link SpringBootTestContextBootstrapper} that can be
 * used to post-process the list of default {@link TestExecutionListener} classes to be
 * used by a test. Can be used to add or remove existing listener classes.
 *

 * @since 1.4.1
 * @see SpringBootTest
 */
@FunctionalInterface
public interface DefaultTestExecutionListenersPostProcessor {

	/**
	 * Post process the list of default {@link TestExecutionListener} classes to be used.
	 * @param listeners the source listeners
	 * @return the actual listeners that should be used
	 */
	Set<Class<? extends TestExecutionListener>> postProcessDefaultTestExecutionListeners(
			Set<Class<? extends TestExecutionListener>> listeners);

}
