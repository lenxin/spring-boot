package org.springframework.boot.autoconfigure.data.couchbase.city;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class City {

	@Id
	private String id;

	@Field
	private String name;

}
