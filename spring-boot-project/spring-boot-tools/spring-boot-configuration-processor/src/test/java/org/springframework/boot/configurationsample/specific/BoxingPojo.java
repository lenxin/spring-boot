package org.springframework.boot.configurationsample.specific;

import org.springframework.boot.configurationsample.ConfigurationProperties;

/**
 * Demonstrate the use of boxing/unboxing. Even if the type does not strictly match, it
 * should still be detected.
 *

 */
@ConfigurationProperties("boxing")
public class BoxingPojo {

	private boolean flag;

	private Integer counter;

	public boolean isFlag() {
		return this.flag;
	}

	// Setter use Boolean
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Integer getCounter() {
		return this.counter;
	}

	// Setter use int
	public void setCounter(int counter) {
		this.counter = counter;
	}

}
