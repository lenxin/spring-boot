package org.springframework.boot.actuate.info;

import org.springframework.util.Assert;

/**
 * A simple {@link InfoContributor} that exposes a single detail.
 *

 * @since 1.4.0
 */
public class SimpleInfoContributor implements InfoContributor {

	private final String prefix;

	private final Object detail;

	public SimpleInfoContributor(String prefix, Object detail) {
		Assert.notNull(prefix, "Prefix must not be null");
		this.prefix = prefix;
		this.detail = detail;
	}

	@Override
	public void contribute(Info.Builder builder) {
		if (this.detail != null) {
			builder.withDetail(this.prefix, this.detail);
		}
	}

}
