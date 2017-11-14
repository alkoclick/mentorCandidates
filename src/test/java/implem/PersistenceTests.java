package implem;

import org.junit.Test;

import model.Mentor;
import model.Opinion;
import util.HibernateTest;

public class PersistenceTests extends HibernateTest {

	@Test
	public void createOpinion() {
		session.save(new Opinion("Student A", "This is an opinion on student A who happens to be an exceptional kid"));
	}

	@Test
	public void createMentor() {
		session.save(new Mentor("Alex", "Pap", "testMe", "Alex is writing java code"));
	}
}
