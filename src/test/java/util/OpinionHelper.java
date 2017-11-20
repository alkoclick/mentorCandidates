package util;

import static org.junit.Assert.assertEquals;

import model.Opinion;

public class OpinionHelper {

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
