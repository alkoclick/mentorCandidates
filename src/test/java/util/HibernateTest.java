package util;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

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
		session.getTransaction().rollback();
		session.close();
	}
}
