package implem.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.hibernate.Session;
import org.junit.Test;

import model.Mentor;
import model.Opinion;
import unit.OpinionTest;
import util.HibernateTest;

public class OpinionPersistenceTests extends HibernateTest {
	private Opinion opinion = new Opinion("Student A", "This is an opinion created standalone");
	private Mentor mentor = new Mentor("Alex", "Pap", "alexPap@gmail.com", "AlexPap is a Java mentor");
	public static int BATCH_SIZE = 20;

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
	 * Batch inserts a number of records to the db and then pulls the total amount
	 */
	@Test
	public void retrieveAll() {
		addOpinionBatch(session);
		assertEquals(session.createQuery("From Opinion o where o.name = \'Student B\'").list().size(), BATCH_SIZE);
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
		OpinionTest.testEquality(dbOpinion, opinion);

		opinion.setMentor(null);
	}

	public static List<Opinion> addOpinionBatch(Session session) {
		List<Opinion> batch = new ArrayList<>();
		IntStream.range(0, BATCH_SIZE).forEach(i -> {
			Opinion currentOpinion = new Opinion("Student B", "B is for batch");
			batch.add(currentOpinion);
			session.save(currentOpinion);
		});
		return batch;
	}
}
