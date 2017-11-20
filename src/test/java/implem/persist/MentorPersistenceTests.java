package implem.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.Test;

import model.Mentor;
import model.Opinion;
import util.HibernateTest;

public class MentorPersistenceTests extends HibernateTest<Mentor> {
	private Opinion opinion = new Opinion("Student A", "This is an opinion created standalone");
	private Mentor mentor = new Mentor("Alex", "Pap", "alexPap@gmail.com", "AlexPap is a Java mentor");

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
		int batchSize = 20;
		IntStream.range(0, batchSize).forEach(i -> {
			service.save(new Mentor("Batch", "User", "batch@spam.com", "This is a batch user"));
		});
		assertEquals(service.count(), batchSize);
	}

	/**
	 * Adds an {@link model.Mentor} record to the database, then retrieves it to
	 * verify it was inserted correctly
	 */
	@Test
	public void retrieveById() {
		service.setModelClass(Mentor.class);
		mentor.getOpinions().clear();
		mentor.getOpinions().add(opinion);
		Mentor savedMentor = service.save(mentor);
		// service.add(opinion);

		service.findAll().forEach(System.out::println);
		Mentor dbmentor = service.findOne(savedMentor.getId());
		assertEquals(dbmentor, mentor);
		assertEquals(dbmentor.getFirstName(), mentor.getFirstName());
		assertEquals(dbmentor.getLastName(), mentor.getLastName());
		assertEquals(dbmentor.getEmail(), mentor.getEmail());
		assertEquals(dbmentor.getDescription(), mentor.getDescription());

		assertEquals(dbmentor.getOpinions().size(), 1);
		dbmentor.getOpinions().forEach(op -> assertEquals(op, opinion));

		mentor.getOpinions().clear();
	}
}
