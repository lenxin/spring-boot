package org.springframework.boot.actuate.endpoint.http;

/**
 * Media types that can be consumed and produced by Actuator endpoints.
 *


 * @since 2.0.0
 */
public final class ActuatorMediaType {

	/**
	 * Constant for the Actuator {@link ApiVersion#V2 v2} media type.
	 */
	public static final String V2_JSON = "application/vnd.spring-boot.actuator.v2+json";

	/**
	 * Constant for the Actuator {@link ApiVersion#V3 v3} media type.
	 */
	public static final String V3_JSON = "application/vnd.spring-boot.actuator.v3+json";

	private ActuatorMediaType() {
	}

}
