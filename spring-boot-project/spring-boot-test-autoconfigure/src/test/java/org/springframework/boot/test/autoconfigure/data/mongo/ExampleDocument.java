package org.springframework.boot.test.autoconfigure.data.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Example document used with {@link DataMongoTest @DataMongoTest} tests.
 *

 */
@Document(collection = "exampleDocuments")
public class ExampleDocument {

	private String id;

	private String text;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
