package org.springframework.boot.autoconfigure.hateoas;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties properties} for Spring HATEOAS.
 *


 * @since 1.2.1
 */
@ConfigurationProperties(prefix = "spring.hateoas")
public class HateoasProperties {

	/**
	 * Whether application/hal+json responses should be sent to requests that accept
	 * application/json.
	 */
	private boolean useHalAsDefaultJsonMediaType = true;

	public boolean getUseHalAsDefaultJsonMediaType() {
		return this.useHalAsDefaultJsonMediaType;
	}

	public void setUseHalAsDefaultJsonMediaType(boolean useHalAsDefaultJsonMediaType) {
		this.useHalAsDefaultJsonMediaType = useHalAsDefaultJsonMediaType;
	}

}
