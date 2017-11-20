package model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * A Mentor (POJO) is a worker responsible for providing opinions
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

	@OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
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

	/**
	 * We override hashcode and equals to improve testing flow
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mentor other = (Mentor) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
