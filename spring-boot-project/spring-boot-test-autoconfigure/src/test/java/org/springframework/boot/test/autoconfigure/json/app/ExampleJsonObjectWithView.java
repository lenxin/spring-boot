package org.springframework.boot.test.autoconfigure.json.app;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.util.ObjectUtils;

/**
 * Example object to read/write as JSON with view
 *

 */
public class ExampleJsonObjectWithView {

	@JsonView(TestView.class)
	private String value;

	private int id;

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		ExampleJsonObjectWithView other = (ExampleJsonObjectWithView) obj;
		return ObjectUtils.nullSafeEquals(this.value, other.value) && ObjectUtils.nullSafeEquals(this.id, other.id);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return this.value + " " + this.id;
	}

	public static class TestView {

	}

}
