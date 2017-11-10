package model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Opinion {
	private long id;
	private String name;
	private String opinion;

	@Id
	@GeneratedValue
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
