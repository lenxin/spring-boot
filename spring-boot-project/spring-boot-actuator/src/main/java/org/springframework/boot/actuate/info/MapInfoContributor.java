package org.springframework.boot.actuate.info;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple {@link InfoContributor} that exposes a map.
 *

 * @since 1.4.0
 */
public class MapInfoContributor implements InfoContributor {

	private final Map<String, Object> info;

	public MapInfoContributor(Map<String, Object> info) {
		this.info = new LinkedHashMap<>(info);
	}

	@Override
	public void contribute(Info.Builder builder) {
		builder.withDetails(this.info);
	}

}
