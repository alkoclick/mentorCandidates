package implem.persist;

import org.junit.Test;

import model.Mentor;
import model.Opinion;
import util.HibernateTest;

public class PersistenceTests extends HibernateTest {

	@Test
	public void createMentorWithOpinion() {
		Mentor mentor = new Mentor("Alex", "Pap", "testMe@test.com", "Alex is writing java code");
		Opinion opinion = new Opinion("Student A", "This is an opinion by a mentor");
		mentor.addOpinion(opinion);
		session.save(mentor);
		session.save(opinion);
	}
}
