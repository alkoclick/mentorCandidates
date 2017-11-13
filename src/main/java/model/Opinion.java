package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Opinion {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Lob
	private String opinion;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOpinion() {
		return opinion;
	}
}
