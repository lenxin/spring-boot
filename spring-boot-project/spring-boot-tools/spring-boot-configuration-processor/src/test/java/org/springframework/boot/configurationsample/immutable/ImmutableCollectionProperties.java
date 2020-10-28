package org.springframework.boot.configurationsample.immutable;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.configurationsample.DefaultValue;

/**
 * Simple immutable properties with collections types and defaults.
 *

 */
@SuppressWarnings("unused")
public class ImmutableCollectionProperties {

	private final List<String> names;

	private final List<Boolean> flags;

	private final List<Duration> durations;

	public ImmutableCollectionProperties(List<String> names, @DefaultValue({ "true", "false" }) List<Boolean> flags,
			@DefaultValue({ "10s", "1m", "1h" }) List<Duration> durations) {
		this.names = names;
		this.flags = flags;
		this.durations = durations;
	}

}
