package org.springframework.boot.docs.context.properties.bind.constructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * A {@link ConfigurationProperties @ConfigurationProperties} example that uses
 * {@link DataSize}.
 *

 */
// tag::example[]
@ConfigurationProperties("app.io")
@ConstructorBinding
public class AppIoProperties {

	private final DataSize bufferSize;

	private final DataSize sizeThreshold;

	public AppIoProperties(@DataSizeUnit(DataUnit.MEGABYTES) @DefaultValue("2MB") DataSize bufferSize,
			@DefaultValue("512B") DataSize sizeThreshold) {
		this.bufferSize = bufferSize;
		this.sizeThreshold = sizeThreshold;
	}

	public DataSize getBufferSize() {
		return this.bufferSize;
	}

	public DataSize getSizeThreshold() {
		return this.sizeThreshold;
	}

}
// end::example[]
