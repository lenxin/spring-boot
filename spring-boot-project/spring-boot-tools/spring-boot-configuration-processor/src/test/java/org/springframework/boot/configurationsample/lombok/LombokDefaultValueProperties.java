package org.springframework.boot.configurationsample.lombok;

import lombok.Data;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Configuration properties with default values.
 *

 */
@Data
@ConfigurationProperties("default")
@SuppressWarnings("unused")
public class LombokDefaultValueProperties {

	private String description = "my description";

}
