package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.session.FlushMode;
import org.springframework.session.SaveMode;

/**
 * Configuration properties for Hazelcast backed Spring Session.
 *

 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "spring.session.hazelcast")
public class HazelcastSessionProperties {

	/**
	 * Name of the map used to store sessions.
	 */
	private String mapName = "spring:session:sessions";

	/**
	 * Sessions flush mode. Determines when session changes are written to the session
	 * store.
	 */
	private FlushMode flushMode = FlushMode.ON_SAVE;

	/**
	 * Sessions save mode. Determines how session changes are tracked and saved to the
	 * session store.
	 */
	private SaveMode saveMode = SaveMode.ON_SET_ATTRIBUTE;

	public String getMapName() {
		return this.mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public FlushMode getFlushMode() {
		return this.flushMode;
	}

	public void setFlushMode(FlushMode flushMode) {
		this.flushMode = flushMode;
	}

	public SaveMode getSaveMode() {
		return this.saveMode;
	}

	public void setSaveMode(SaveMode saveMode) {
		this.saveMode = saveMode;
	}

}
