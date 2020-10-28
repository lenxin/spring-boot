package org.springframework.boot.actuate.autoconfigure.metrics.export.properties;

/**
 * Base test for {@link StepRegistryPropertiesConfigAdapter} implementations.
 *
 * @param <P> properties used by the tests
 * @param <A> adapter used by the tests


 */
public abstract class StepRegistryPropertiesConfigAdapterTests<P extends StepRegistryProperties, A extends StepRegistryPropertiesConfigAdapter<P>>
		extends PushRegistryPropertiesConfigAdapterTests<P, A> {

}
