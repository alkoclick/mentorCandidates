package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import model.Mentor;

public class MentorHelper {

	/**
	 * A helper method for ensuring two {@link model.Mentor} objects are identical
	 * 
	 * @param a
	 *            The first {@link model.Mentor}
	 * @param b
	 *            The second {@link model.Mentor}
	 */
	public static void testEquality(Mentor a, Mentor b) {
		// This will take care of checking ID
		assertEquals(a, b);
		assertEquals(a.getFirstName(), b.getFirstName());
		assertEquals(a.getLastName(), b.getLastName());
		assertEquals(a.getEmail(), b.getEmail());
		a.getOpinions().forEach(op -> assertTrue(b.getOpinions().contains(op)));
		assertEquals(a.getDescription(), b.getDescription());
	}
}
