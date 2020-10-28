package org.springframework.boot.build.context.properties;

/**
 * Abstract class for entries in {@link ConfigurationTable}.
 *

 */
abstract class ConfigurationTableEntry implements Comparable<ConfigurationTableEntry> {

	protected String key;

	String getKey() {
		return this.key;
	}

	abstract void write(AsciidocBuilder builder);

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ConfigurationTableEntry other = (ConfigurationTableEntry) obj;
		return this.key.equals(other.key);
	}

	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

	@Override
	public int compareTo(ConfigurationTableEntry other) {
		return this.key.compareTo(other.getKey());
	}

}
