package implem.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import model.Mentor;
import model.Opinion;
import util.HibernateTest;
import util.OpinionHelper;

public class OpinionPersistenceTests extends HibernateTest<Opinion> {
	private Opinion opinion = new Opinion("Student A", "This is an opinion created standalone");
	private Mentor mentor = new Mentor("Alex", "Pap", "alexPap@gmail.com", "AlexPap is a Java mentor");

	@Before
	@Override
	public void setClass() {
		service.setModelClass(Opinion.class);
	}

	/**
	 * Tests to ensure that a record can be written to the database and a proper ID
	 * generated for it
	 */
	@Test
	public void addToDb() {
		service.save(opinion);
		assertNotEquals(opinion.getId(), 0);
		assertTrue(service.exists(opinion.getId()));
	}

	/**
	 * Batch inserts a number of records to the db and then pulls the total amount
	 */
	@Test
	public void retrieveAll() {
		List<Opinion> opinions = IntStream.range(0, BATCH_SIZE).mapToObj(i -> {
			Opinion currentOpinion = new Opinion("Student B", "B is for batch");
			service.save(currentOpinion);
			return currentOpinion;
		}).collect(Collectors.toList());

		assertEquals(service.count(), BATCH_SIZE);

		opinions.forEach(op -> service.delete(op));
	}

	/**
	 * Adds an {@link model.Opinion} record to the database, then retrieves it to
	 * verify it was inserted correctly
	 */
	@Test
	public void retrieveById() {
		opinion.setMentor(mentor);

		service.save(opinion);
		// service.add(mentor);

		Opinion dbOpinion = service.findOne(opinion.getId());
		OpinionHelper.testEquality(dbOpinion, opinion);

		opinion.setMentor(null);
	}
}
