package smoketest.data.couchbase;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document
public class User {

	@Id
	private String id;

	@Field
	private String firstName;

	@Field
	private String lastName;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User{id='" + this.id + '\'' + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
				+ '\'' + '}';
	}

}
