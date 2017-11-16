package util;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

public class HibernateTest {

	protected Session session;

	@Before
	public void obtainSession() {
		session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
	}

	@After
	public void saveAndClose() {
		session.getTransaction().commit();
		session.close();
	}

	@AfterClass
	public static void cleanUp() {

	}
}
