package model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * A Mentor (POJO) is a worker responsible for providing opinions
 * 
 * @author Alex
 *
 */
@Entity
public class Mentor {

	@Id
	@GeneratedValue
	private long id;

	@Column
	@NotNull
	@Length(max = 100)
	private String firstName;

	@Column
	@NotNull
	@Length(max = 100)
	private String lastName;

	@Column
	@Email
	@NotNull
	@Length(max = 100)
	private String email;

	@OneToMany(mappedBy = "mentor")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Opinion> opinions;

	@Column
	@Lob
	private String description;

	public Mentor() {
	}

	public Mentor(String firstName, String lastName, String email, String description) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.description = description;
		this.opinions = new HashSet<>();
	}

	public void addOpinion(Opinion opinion) {
		opinions.add(opinion);
		opinion.setMentor(this);
	}

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

	public Collection<Opinion> getOpinions() {
		return opinions;
	}
}
