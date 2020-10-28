package org.springframework.boot.actuate.mongo;

import com.mongodb.MongoException;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link MongoHealthIndicator}.
 *

 */
class MongoHealthIndicatorTests {

	@Test
	void mongoIsUp() {
		Document commandResult = mock(Document.class);
		given(commandResult.getString("version")).willReturn("2.6.4");
		MongoTemplate mongoTemplate = mock(MongoTemplate.class);
		given(mongoTemplate.executeCommand("{ buildInfo: 1 }")).willReturn(commandResult);
		MongoHealthIndicator healthIndicator = new MongoHealthIndicator(mongoTemplate);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails().get("version")).isEqualTo("2.6.4");
		verify(commandResult).getString("version");
		verify(mongoTemplate).executeCommand("{ buildInfo: 1 }");
	}

	@Test
	void mongoIsDown() {
		MongoTemplate mongoTemplate = mock(MongoTemplate.class);
		given(mongoTemplate.executeCommand("{ buildInfo: 1 }")).willThrow(new MongoException("Connection failed"));
		MongoHealthIndicator healthIndicator = new MongoHealthIndicator(mongoTemplate);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat((String) health.getDetails().get("error")).contains("Connection failed");
		verify(mongoTemplate).executeCommand("{ buildInfo: 1 }");
	}

}
