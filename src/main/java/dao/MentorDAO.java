package dao;

import java.util.Collection;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import model.Mentor;
import util.SessionBuilder;

@Repository
public class MentorDAO implements DAO<Mentor> {

	@Override
	public void add(Mentor m) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.save(m);
	}

	@Override
	public Collection<Mentor> getAll() {
		Session session = SessionBuilder.getSessionFactory().openSession();
		return session.createNativeQuery("select * from " + Mentor.class.getSimpleName() + ";", Mentor.class)
				.getResultList();
	}

	@Override
	public Mentor getById(long id) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		return session.find(Mentor.class, id);
	}

	@Override
	public void delete(Mentor m) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.delete(m);
	}

}
