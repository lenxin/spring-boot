package smoketest.jpa.repository;

import java.util.List;

import smoketest.jpa.domain.Note;

public interface NoteRepository {

	List<Note> findAll();

}
