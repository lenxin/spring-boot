package smoketest.jpa.repository;

import java.util.List;

import smoketest.jpa.domain.Tag;

public interface TagRepository {

	List<Tag> findAll();

}
