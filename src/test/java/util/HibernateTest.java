package util;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

import model.Opinion;

@Transactional
public class HibernateTest {

	protected Session session;

	@Before
	public void obtainSession() {
		session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
	}

	@After
	public void saveAndClose() {
		session.close();
	}

	protected void createSchema() {
		Opinion opinion = new Opinion("a", "b");
		session.save(opinion);
		session.getTransaction().commit();
		session.beginTransaction();
		session.delete(opinion);
		session.getTransaction().commit();
	}
}
