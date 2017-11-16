package implem.persist;

import org.junit.Test;

import model.Mentor;

public class MentorPersistenceTests extends PersistenceTests {

	@Test
	public void addToDb() {
		session.save(new Mentor("Alex", "Pap", "testMe@test.com", "Alex is writing java code"));
	}

	@Test
	public void retrieveAll() {

	}

	@Test
	public void retrieveById() {

	}
}
