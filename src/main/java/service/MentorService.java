package service;

import dao.MentorDAO;
import model.Mentor;

public class MentorService extends ModelService<Mentor> {

	public MentorService() {
		modelDAO = new MentorDAO();
	}

}
