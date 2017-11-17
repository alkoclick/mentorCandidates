package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An opinion POJO represents the opinion of a mentor for a student
 */
@Entity
public class Opinion {

	@Id
	@GeneratedValue
	private long id;

	@Column
	@NotNull
	@Length(max = 100)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mentor_id")
	@JsonIgnore
	private Mentor mentor;

	@Column
	@Lob
	@NotNull
	private String description;

	public Opinion() {
	}

	public Opinion(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setMentor(Mentor mentor) {
		this.mentor = mentor;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * We override hashCode and equals because mentors maintain a hashset of their
	 * opinions
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
		Opinion other = (Opinion) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Mentor getMentor() {
		return mentor;
	}

	@Override
	public String toString() {
		return "Opinion [id=" + id + ", name=" + name + ", mentor=" + mentor + ", description=" + description + "]";
	}

}
