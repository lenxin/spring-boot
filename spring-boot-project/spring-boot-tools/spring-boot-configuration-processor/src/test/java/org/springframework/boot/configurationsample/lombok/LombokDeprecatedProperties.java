package org.springframework.boot.configurationsample.lombok;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Deprecated configuration properties.
 *

 */
@Getter
@Setter
@ConfigurationProperties(prefix = "deprecated")
@Deprecated
@SuppressWarnings("unused")
public class LombokDeprecatedProperties {

	private String name;

	private String description;

}
