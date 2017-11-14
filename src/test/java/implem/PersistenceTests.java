package implem;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Mentor;
import model.Opinion;
import util.SessionBuilder;

public class PersistenceTests {

	private Session session;

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

	@Test
	public void createOpinion() {
		session.save(new Opinion("Student A", "This is an opinion on student A who happens to be an exceptional kid"));
	}

	@Test
	public void createMentor() {
		session.save(new Mentor("Alex", "Pap", "testMe", "Alex is writing java code"));
	}
}
