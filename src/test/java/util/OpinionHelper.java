package util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import org.hibernate.Session;

import implem.persist.OpinionPersistenceTests;
import model.Opinion;

public class OpinionHelper {

	public static void deleteBatch(Collection<?> toDelete, Session session) {
		session.beginTransaction();
		toDelete.forEach(op -> session.delete(op));
		session.getTransaction().commit();
	}

	public static List<Opinion> addOpinionBatch(Session session) {
		List<Opinion> batch = new ArrayList<>();
		IntStream.range(0, OpinionPersistenceTests.BATCH_SIZE).forEach(i -> {
			Opinion currentOpinion = new Opinion("Student B", "B is for batch");
			batch.add(currentOpinion);
			session.save(currentOpinion);
		});
		session.getTransaction().commit();
		return batch;
	}

	/**
	 * A helper method for ensuring two {@link model.Opinion} objects are identical
	 * 
	 * @param a
	 *            The first {@link model.Opinion}
	 * @param b
	 *            The second {@link model.Opinion}
	 */
	public static void testEquality(Opinion a, Opinion b) {
		// This will take care of checking ID
		assertEquals(a, b);
		assertEquals(a.getName(), b.getName());
		assertEquals(a.getDescription(), b.getDescription());
		assertEquals(a.getMentor(), b.getMentor());
	}
}
