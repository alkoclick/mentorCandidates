package model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Mentor {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String description;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getDescription() {
		return description;
	}
}
