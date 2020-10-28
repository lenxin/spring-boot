package org.springframework.boot.configurationsample.lombok;

import lombok.Data;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Configuration properties with a single deprecated element.
 *

 */
@Data
@ConfigurationProperties("singledeprecated")
@SuppressWarnings("unused")
public class LombokDeprecatedSingleProperty {

	@Deprecated
	private String name;

	private String description;

}
