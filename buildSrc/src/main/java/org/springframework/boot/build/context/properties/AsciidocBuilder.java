package org.springframework.boot.build.context.properties;

/**
 * Simple builder to help construct Asciidoc markup.
 *

 */
class AsciidocBuilder {

	private final StringBuilder content;

	AsciidocBuilder() {
		this.content = new StringBuilder();
	}

	AsciidocBuilder appendKey(Object... items) {
		for (Object item : items) {
			appendln("`+", item, "+` +");
		}
		return this;
	}

	AsciidocBuilder newLine() {
		return append(System.lineSeparator());
	}

	AsciidocBuilder appendln(Object... items) {
		return append(items).newLine();
	}

	AsciidocBuilder append(Object... items) {
		for (Object item : items) {
			this.content.append(item);
		}
		return this;
	}

	@Override
	public String toString() {
		return this.content.toString();
	}

}
