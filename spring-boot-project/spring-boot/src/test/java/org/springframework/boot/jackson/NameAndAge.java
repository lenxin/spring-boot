package org.springframework.boot.jackson;

import org.springframework.util.ObjectUtils;

/**
 * Sample object used for tests.
 *


 */
public final class NameAndAge extends Name {

	private final int age;

	public NameAndAge(String name, int age) {
		super(name);
		this.age = age;
	}

	public int getAge() {
		return this.age;
	}

	public String asKey() {
		return this.name + " is " + this.age;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof NameAndAge) {
			NameAndAge other = (NameAndAge) obj;
			boolean rtn = true;
			rtn = rtn && ObjectUtils.nullSafeEquals(this.name, other.name);
			rtn = rtn && ObjectUtils.nullSafeEquals(this.age, other.age);
			return rtn;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ObjectUtils.nullSafeHashCode(this.name);
		result = prime * result + ObjectUtils.nullSafeHashCode(this.age);
		return result;
	}

}
