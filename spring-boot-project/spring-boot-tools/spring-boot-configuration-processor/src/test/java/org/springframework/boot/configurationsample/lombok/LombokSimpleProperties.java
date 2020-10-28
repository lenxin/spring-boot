package org.springframework.boot.configurationsample.lombok;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Configuration properties using lombok @Getter/@Setter at class level.
 *

 */
@Getter
@Setter
@ConfigurationProperties(prefix = "simple")
@SuppressWarnings("unused")
public class LombokSimpleProperties {

	private final String id = "super-id";

	/**
	 * Name description.
	 */
	private String name;

	private String description;

	private Integer counter;

	@Deprecated
	private Integer number = 0;

	private final List<String> items = new ArrayList<>();

	private final String ignored = "foo";

}
