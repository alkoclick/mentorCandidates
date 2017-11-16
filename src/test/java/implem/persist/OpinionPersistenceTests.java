package implem.persist;

import org.junit.Test;

import model.Opinion;

public class OpinionPersistenceTests extends PersistenceTests {

	@Test
	public void addToDb() {
		session.save(new Opinion("Student A", "This is an opinion created standalone"));
	}

	@Test
	public void retrieveAll() {

	}

	@Test
	public void retrieveById() {

	}
}
