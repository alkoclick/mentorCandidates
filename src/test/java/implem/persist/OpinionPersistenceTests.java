package implem.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.stream.IntStream;

import org.junit.Test;

import model.Mentor;
import model.Opinion;

public class OpinionPersistenceTests extends PersistenceTests {
	private Opinion opinion = new Opinion("Student A", "This is an opinion created standalone");
	private Mentor mentor = new Mentor("Alex", "Pap", "alexPap@gmail.com", "AlexPap is a Java mentor");

	/**
	 * Tests to ensure that a record can be written to the database and a proper ID
	 * generated for it
	 */
	@Test
	public void addToDb() {
		Serializable id = session.save(opinion);
		assertNotEquals(id, 0);
		assertEquals(id, opinion.getId());
		assertTrue(session.contains(opinion));
	}

	/**
	 * Batch inserts a number of items to the db and then pulls the total amount
	 */
	@Test
	public void retrieveAll() {
		int batchSize = 20;
		IntStream.range(0, batchSize).forEach(i -> {
			session.save(new Opinion("Student B", "B is for batch"));
		});
		assertEquals(session.createQuery("From Opinion o where o.name = \'Student B\'").list().size(), batchSize);
	}

	/**
	 * Adds an {@link model.Opinion} record to the database, then retrieves it to
	 * verify it was inserted correctly
	 */
	@Test
	public void retrieveById() {
		opinion.setMentor(mentor);

		session.save(opinion);
		session.save(mentor);

		Opinion dbOpinion = session.find(Opinion.class, opinion.getId());
		assertEquals(dbOpinion, opinion);
		assertEquals(dbOpinion.getName(), opinion.getName());
		assertEquals(dbOpinion.getDescription(), opinion.getDescription());
		assertEquals(dbOpinion.getMentor(), opinion.getMentor());

		opinion.setMentor(null);
	}
}
