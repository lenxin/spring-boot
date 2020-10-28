package org.springframework.boot.autoconfigure.data.couchbase;

import org.junit.jupiter.api.Test;

import org.springframework.data.couchbase.core.convert.DefaultCouchbaseTypeMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CouchbaseDataProperties}.
 *

 */
class CouchbaseDataPropertiesTests {

	@Test
	void typeKeyHasConsistentDefault() {
		assertThat(new CouchbaseDataProperties().getTypeKey()).isEqualTo(DefaultCouchbaseTypeMapper.DEFAULT_TYPE_KEY);
	}

}
