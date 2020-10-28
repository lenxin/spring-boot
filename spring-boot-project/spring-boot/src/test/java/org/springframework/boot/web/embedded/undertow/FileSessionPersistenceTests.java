package org.springframework.boot.web.embedded.undertow;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import io.undertow.servlet.api.SessionPersistenceManager.PersistentSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FileSessionPersistence}.
 *

 */
class FileSessionPersistenceTests {

	private File dir;

	private FileSessionPersistence persistence;

	private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	private Date expiration = new Date(System.currentTimeMillis() + 10000);

	@BeforeEach
	void setup(@TempDir File tempDir) throws IOException {
		this.dir = tempDir;
		this.dir.mkdir();
		this.persistence = new FileSessionPersistence(this.dir);
	}

	@Test
	void loadsNullForMissingFile() {
		Map<String, PersistentSession> attributes = this.persistence.loadSessionAttributes("test", this.classLoader);
		assertThat(attributes).isNull();
	}

	@Test
	void persistAndLoad() {
		Map<String, PersistentSession> sessionData = new LinkedHashMap<>();
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("spring", "boot");
		PersistentSession session = new PersistentSession(this.expiration, data);
		sessionData.put("abc", session);
		this.persistence.persistSessions("test", sessionData);
		Map<String, PersistentSession> restored = this.persistence.loadSessionAttributes("test", this.classLoader);
		assertThat(restored).isNotNull();
		assertThat(restored.get("abc").getExpiration()).isEqualTo(this.expiration);
		assertThat(restored.get("abc").getSessionData().get("spring")).isEqualTo("boot");
	}

	@Test
	void dontRestoreExpired() {
		Date expired = new Date(System.currentTimeMillis() - 1000);
		Map<String, PersistentSession> sessionData = new LinkedHashMap<>();
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("spring", "boot");
		PersistentSession session = new PersistentSession(expired, data);
		sessionData.put("abc", session);
		this.persistence.persistSessions("test", sessionData);
		Map<String, PersistentSession> restored = this.persistence.loadSessionAttributes("test", this.classLoader);
		assertThat(restored).isNotNull();
		assertThat(restored.containsKey("abc")).isFalse();
	}

	@Test
	void deleteFileOnClear() {
		File sessionFile = new File(this.dir, "test.session");
		Map<String, PersistentSession> sessionData = new LinkedHashMap<>();
		this.persistence.persistSessions("test", sessionData);
		assertThat(sessionFile.exists()).isTrue();
		this.persistence.clear("test");
		assertThat(sessionFile.exists()).isFalse();
	}

}
