package util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;

import implem.persist.OpinionPersistenceTests;
import model.Opinion;

public class OpinionHelper {

	public static void deleteBatch(Collection<?> toDelete, EntityManager em) {
		em.getTransaction().begin();
		toDelete.forEach(op -> em.remove(op));
		em.getTransaction().commit();
	}

	public static List<Opinion> addOpinionBatch(EntityManager em) {
		List<Opinion> batch = new ArrayList<>();
		IntStream.range(0, OpinionPersistenceTests.BATCH_SIZE).forEach(i -> {
			Opinion currentOpinion = new Opinion("Student B", "B is for batch");
			batch.add(currentOpinion);
			em.persist(currentOpinion);
		});
		em.getTransaction().commit();
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
