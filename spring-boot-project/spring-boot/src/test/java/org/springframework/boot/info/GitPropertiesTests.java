package org.springframework.boot.info;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GitProperties}.
 *

 */
class GitPropertiesTests {

	@Test
	void basicInfo() {
		GitProperties properties = new GitProperties(
				createProperties("master", "abcdefghijklmno", "abcdefg", "1457527123"));
		assertThat(properties.getBranch()).isEqualTo("master");
		assertThat(properties.getCommitId()).isEqualTo("abcdefghijklmno");
		assertThat(properties.getShortCommitId()).isEqualTo("abcdefg");
	}

	@Test
	void noInfo() {
		GitProperties properties = new GitProperties(new Properties());
		assertThat(properties.getBranch()).isNull();
		assertThat(properties.getCommitId()).isNull();
		assertThat(properties.getShortCommitId()).isNull();
		assertThat(properties.getCommitTime()).isNull();
	}

	@Test
	void coerceEpochSecond() {
		GitProperties properties = new GitProperties(createProperties("master", "abcdefg", null, "1457527123"));
		assertThat(properties.getCommitTime()).isNotNull();
		assertThat(properties.get("commit.time")).isEqualTo("1457527123000");
		assertThat(properties.getCommitTime().toEpochMilli()).isEqualTo(1457527123000L);
	}

	@Test
	void coerceDateString() {
		GitProperties properties = new GitProperties(
				createProperties("master", "abcdefg", null, "2016-03-04T14:36:33+0100"));
		assertThat(properties.getCommitTime()).isNotNull();
		assertThat(properties.get("commit.time")).isEqualTo("1457098593000");
		assertThat(properties.getCommitTime().toEpochMilli()).isEqualTo(1457098593000L);
	}

	@Test
	void coerceUnsupportedFormat() {
		GitProperties properties = new GitProperties(
				createProperties("master", "abcdefg", null, "2016-03-04 15:22:24"));
		assertThat(properties.getCommitTime()).isNull();
		assertThat(properties.get("commit.time")).isEqualTo("2016-03-04 15:22:24");
	}

	@Test
	void shortCommitUsedIfPresent() {
		GitProperties properties = new GitProperties(
				createProperties("master", "abcdefghijklmno", "abcdefgh", "1457527123"));
		assertThat(properties.getCommitId()).isEqualTo("abcdefghijklmno");
		assertThat(properties.getShortCommitId()).isEqualTo("abcdefgh");
	}

	@Test
	void shortenCommitIdShorterThan7() {
		GitProperties properties = new GitProperties(createProperties("master", "abc", null, "1457527123"));
		assertThat(properties.getCommitId()).isEqualTo("abc");
		assertThat(properties.getShortCommitId()).isEqualTo("abc");
	}

	@Test
	void shortenCommitIdLongerThan7() {
		GitProperties properties = new GitProperties(createProperties("master", "abcdefghijklmno", null, "1457527123"));
		assertThat(properties.getCommitId()).isEqualTo("abcdefghijklmno");
		assertThat(properties.getShortCommitId()).isEqualTo("abcdefg");
	}

	private static Properties createProperties(String branch, String commitId, String commitIdAbbrev,
			String commitTime) {
		Properties properties = new Properties();
		properties.put("branch", branch);
		properties.put("commit.id", commitId);
		if (commitIdAbbrev != null) {
			properties.put("commit.id.abbrev", commitIdAbbrev);
		}
		properties.put("commit.time", commitTime);
		return properties;
	}

}
