package org.springframework.boot.test.autoconfigure.data.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Example graph used with {@link DataRedisTest @DataRedisTest} tests.
 *

 */
@RedisHash("persons")
public class PersonHash {

	@Id
	private String id;

	private String description;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
