package org.springframework.boot.configurationsample.lombok;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Configuration properties using lombok @Getter/@Setter at field level.
 *

 */
@ConfigurationProperties(prefix = "explicit")
public class LombokExplicitProperties {

	@Getter
	private final String id = "super-id";

	/**
	 * Name description.
	 */
	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String description;

	@Getter
	@Setter
	private Integer counter;

	@Deprecated
	@Getter
	@Setter
	private Integer number = 0;

	@Getter
	private final List<String> items = new ArrayList<>();

	// Should be ignored if no annotation is set
	@SuppressWarnings("unused")
	private String ignored;

	@Getter
	private String ignoredOnlyGetter;

	@Setter
	private String ignoredOnlySetter;

}
