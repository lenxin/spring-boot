package org.springframework.boot.autoconfigure.data.redis;

import org.junit.jupiter.api.Test;

import org.springframework.boot.diagnostics.FailureAnalysis;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RedisUrlSyntaxFailureAnalyzer}.
 *

 */
class RedisUrlSyntaxFailureAnalyzerTests {

	@Test
	void analyzeInvalidUrlSyntax() {
		RedisUrlSyntaxException exception = new RedisUrlSyntaxException("redis://invalid");
		FailureAnalysis analysis = new RedisUrlSyntaxFailureAnalyzer().analyze(exception);
		assertThat(analysis.getDescription()).contains("The URL 'redis://invalid' is not valid");
		assertThat(analysis.getAction()).contains("Review the value of the property 'spring.redis.url'");
	}

	@Test
	void analyzeRedisHttpUrl() {
		RedisUrlSyntaxException exception = new RedisUrlSyntaxException("http://127.0.0.1:26379/mymaster");
		FailureAnalysis analysis = new RedisUrlSyntaxFailureAnalyzer().analyze(exception);
		assertThat(analysis.getDescription()).contains("The URL 'http://127.0.0.1:26379/mymaster' is not valid")
				.contains("The scheme 'http' is not supported");
		assertThat(analysis.getAction()).contains("Use the scheme 'redis://` for insecure or `rediss://` for secure");
	}

	@Test
	void analyzeRedisSentinelUrl() {
		RedisUrlSyntaxException exception = new RedisUrlSyntaxException(
				"redis-sentinel://username:password@127.0.0.1:26379,127.0.0.1:26380/mymaster");
		FailureAnalysis analysis = new RedisUrlSyntaxFailureAnalyzer().analyze(exception);
		assertThat(analysis.getDescription()).contains(
				"The URL 'redis-sentinel://username:password@127.0.0.1:26379,127.0.0.1:26380/mymaster' is not valid")
				.contains("The scheme 'redis-sentinel' is not supported");
		assertThat(analysis.getAction()).contains("Use spring.redis.sentinel properties");
	}

	@Test
	void analyzeRedisSocketUrl() {
		RedisUrlSyntaxException exception = new RedisUrlSyntaxException("redis-socket:///redis/redis.sock");
		FailureAnalysis analysis = new RedisUrlSyntaxFailureAnalyzer().analyze(exception);
		assertThat(analysis.getDescription()).contains("The URL 'redis-socket:///redis/redis.sock' is not valid")
				.contains("The scheme 'redis-socket' is not supported");
		assertThat(analysis.getAction()).contains("Configure the appropriate Spring Data Redis connection beans");
	}

}
