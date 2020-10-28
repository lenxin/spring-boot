package smoketest.jpa.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Tag {

	@Id
	@SequenceGenerator(name = "tag_generator", sequenceName = "tag_sequence", initialValue = 4)
	@GeneratedValue(generator = "tag_generator")
	private long id;

	private String name;

	@ManyToMany(mappedBy = "tags")
	private List<Note> notes;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

}
