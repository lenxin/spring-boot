package org.springframework.boot.actuate.autoconfigure.metrics.export.properties;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base test for {@link PushRegistryPropertiesConfigAdapter} implementations.
 *
 * @param <P> properties used by the tests
 * @param <A> adapter used by the tests


 */
public abstract class PushRegistryPropertiesConfigAdapterTests<P extends PushRegistryProperties, A extends PushRegistryPropertiesConfigAdapter<P>> {

	protected abstract P createProperties();

	protected abstract A createConfigAdapter(P properties);

	@Test
	void whenPropertiesStepIsSetAdapterStepReturnsIt() {
		P properties = createProperties();
		properties.setStep(Duration.ofSeconds(42));
		assertThat(createConfigAdapter(properties).step()).hasSeconds(42);
	}

	@Test
	void whenPropertiesEnabledIsSetAdapterEnabledReturnsIt() {
		P properties = createProperties();
		properties.setEnabled(false);
		assertThat(createConfigAdapter(properties).enabled()).isFalse();
	}

	@Test
	void whenPropertiesBatchSizeIsSetAdapterBatchSizeReturnsIt() {
		P properties = createProperties();
		properties.setBatchSize(10042);
		assertThat(createConfigAdapter(properties).batchSize()).isEqualTo(10042);
	}

}
