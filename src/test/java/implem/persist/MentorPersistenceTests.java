package implem.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import model.Mentor;
import model.Opinion;
import util.HibernateTest;
import util.MentorHelper;

public class MentorPersistenceTests extends HibernateTest<Mentor> {
	private Opinion opinion = new Opinion("Student A", "This is an opinion created standalone");
	private Mentor mentor = new Mentor("Alex", "Pap", "alexPap@gmail.com", "AlexPap is a Java mentor");

	@Before
	@Override
	public void setClass() {
		service.setModelClass(Mentor.class);
	}

	/**
	 * Tests to ensure that a record can be written to the database and a proper ID
	 * generated for it
	 */
	@Test
	public void addToDb() {
		service.save(mentor);
		assertNotEquals(mentor.getId(), 0);
		assertTrue(service.exists(mentor.getId()));
	}

	/**
	 * Batch inserts a number of records to the db and then pulls the total amount
	 */
	@Test
	public void retrieveAll() {
		IntStream.range(0, BATCH_SIZE).forEach(i -> {
			service.save(new Mentor("Batch", "User", "batch@spam.com", "This is a batch user"));
		});
		assertEquals(service.count(), BATCH_SIZE);
	}

	/**
	 * Adds an {@link model.Mentor} record to the database, then retrieves it to
	 * verify it was inserted correctly
	 */
	@Test
	public void retrieveById() {
		mentor.getOpinions().clear();
		mentor.addOpinion(opinion);
		Mentor savedMentor = service.save(mentor);

		Mentor dbmentor = service.findOne(savedMentor.getId());
		MentorHelper.testEquality(savedMentor, dbmentor);
		assertEquals(dbmentor.getOpinions().size(), 1);

		mentor.getOpinions().clear();
	}
}
