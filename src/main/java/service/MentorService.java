package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dao.MentorDAO;
import model.Mentor;

@Service
public class MentorService implements EntityService<Mentor> {

	private MentorDAO mentorDAO;

	public MentorService() {
		mentorDAO = new MentorDAO();
	}

	public void setMentorDAO(MentorDAO mentorDAO) {
		this.mentorDAO = mentorDAO;
	}

	@Override
	@Transactional
	public void add(Mentor m) {
		this.mentorDAO.add(m);
	}

	@Override
	@Transactional
	public Collection<Mentor> getAll() {
		return this.mentorDAO.getAll();
	}

	@Override
	@Transactional
	public Mentor getById(long id) {
		return this.mentorDAO.getById(id);
	}

	@Override
	@Transactional
	public void delete(Mentor m) {
		this.mentorDAO.delete(m);
	}

}
