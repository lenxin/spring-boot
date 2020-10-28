package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;
import org.springframework.boot.configurationsample.simple.SimpleProperties;

/**
 * Properties that conflict with {@link SimpleProperties}.
 *

 */
@ConfigurationProperties("simple")
public class SimpleConflictingProperties {

	private String flag = "hello";

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
