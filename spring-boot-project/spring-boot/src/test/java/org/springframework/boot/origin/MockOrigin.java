package org.springframework.boot.origin;

import org.springframework.util.Assert;

/**
 * Mock {@link Origin} implementation used for testing.
 *

 */
public final class MockOrigin implements Origin {

	private final String value;

	private final Origin parent;

	private MockOrigin(String value, Origin parent) {
		Assert.notNull(value, "Value must not be null");
		this.value = value;
		this.parent = parent;
	}

	@Override
	public Origin getParent() {
		return this.parent;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return this.value.equals(((MockOrigin) obj).value);
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public String toString() {
		return this.value;
	}

	public static Origin of(String value) {
		return of(value, null);
	}

	public static Origin of(String value, Origin parent) {
		return (value != null) ? new MockOrigin(value, parent) : null;
	}

}
