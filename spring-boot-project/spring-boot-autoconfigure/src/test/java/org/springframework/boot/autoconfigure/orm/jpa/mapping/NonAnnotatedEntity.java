package org.springframework.boot.autoconfigure.orm.jpa.mapping;

/**
 * A non annotated entity that is handled by a custom "mapping-file".
 *

 */
public class NonAnnotatedEntity {

	private Long id;

	private String value;

	protected NonAnnotatedEntity() {
	}

	public NonAnnotatedEntity(String value) {
		this.value = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
