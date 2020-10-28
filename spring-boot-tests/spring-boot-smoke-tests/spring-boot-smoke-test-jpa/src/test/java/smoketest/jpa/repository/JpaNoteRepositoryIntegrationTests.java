package smoketest.jpa.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import smoketest.jpa.domain.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link JpaNoteRepository}.
 *

 */
@SpringBootTest
@Transactional
class JpaNoteRepositoryIntegrationTests {

	@Autowired
	JpaNoteRepository repository;

	@Test
	void findsAllNotes() {
		List<Note> notes = this.repository.findAll();
		assertThat(notes).hasSize(4);
		for (Note note : notes) {
			assertThat(note.getTags().size()).isGreaterThan(0);
		}
	}

}
