package org.springframework.boot.actuate.autoconfigure.system;

import java.io.File;

import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.util.unit.DataSize;

/**
 * External configuration properties for {@link DiskSpaceHealthIndicator}.
 *


 * @since 1.2.0
 */
@ConfigurationProperties(prefix = "management.health.diskspace")
public class DiskSpaceHealthIndicatorProperties {

	/**
	 * Path used to compute the available disk space.
	 */
	private File path = new File(".");

	/**
	 * Minimum disk space that should be available.
	 */
	private DataSize threshold = DataSize.ofMegabytes(10);

	public File getPath() {
		return this.path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public DataSize getThreshold() {
		return this.threshold;
	}

	public void setThreshold(DataSize threshold) {
		Assert.isTrue(!threshold.isNegative(), "threshold must be greater than or equal to 0");
		this.threshold = threshold;
	}

}
