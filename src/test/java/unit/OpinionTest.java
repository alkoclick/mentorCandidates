package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Mentor;
import model.Opinion;
import util.ValidationTest;

public class OpinionTest extends ValidationTest {

	private static Mentor mentor;

	@BeforeClass
	public static void SetUp() {
		mentor = new Mentor("Alex", "Pap", "alexPap@gmail.com", "AlexPap is a Java mentor");
	}

	/**
	 * Verifies that a single {@link model.Opinion} object is correctly instantiated
	 */
	@Test
	public void testObjectInstantiation() {
		String studentName, description;
		Opinion opinion = new Opinion(studentName = "Jay",
				description = "Jay is an aspiring Java programmer, who appears to be quite motivated");

		// Not loaded into DB yet so...
		assertTrue(opinion.getId() == 0);
		assertNull(opinion.getMentor());

		assertEquals(opinion.getName(), studentName);
		assertEquals(opinion.getDescription(), description);

		opinion.setMentor(mentor);
		assertEquals(opinion.getMentor(), mentor);
	}

	/**
	 * Ensure that the {@link javax.validation.constraints.NotNull} annotation
	 * generates the proper runtime checks for hibernate
	 * 
	 * @see <a href=
	 *      "https://docs.jboss.org/hibernate/validator/3.1/reference/en/html_single/">Hibernate
	 *      validator reference</a>
	 */
	@Test
	public void validateNulls() {

		// Ensure NotNull annotation works
		assertEquals(validator.validate(new Opinion(null, " ")).size(), 1);
		assertEquals(validator.validate(new Opinion(" ", null)).size(), 1);

		// Ensure combinations of nulls report all errors
		assertEquals(validator.validate(new Opinion(null, null)).size(), 2);

		// Ensure empty string doesn't trigger NotNull
		assertEquals(validator.validate(new Opinion("", "")).size(), 0);
	}

	/**
	 * Validate some "normal" entries with the expected input
	 */
	@Test
	public void validateCorrect() {
		Opinion opinion1 = new Opinion("Jay",
				"Jay is an aspiring Java programmer, who appears to be quite motivated. His main weakness is thinking outside the established norm");
		assertEquals(validator.validate(opinion1).size(), 0);
		assertEquals(validator.validate(new Opinion("Julie",
				"Julie has an exceptional aptitude in problem solving, but she appears to feel unchallenged and demotivated when facing simpler tasks"))
				.size(), 0);
		opinion1.setMentor(mentor);
		assertEquals(validator.validate(opinion1).size(), 0);
	}

	/**
	 * Validate some entries with higher complexity than the average case. The email
	 * field is not tested so heavily as it is validated extensively in
	 * {@link #validateEmail()}
	 */
	@Test
	public void validateComplex() {

		// Oversized strings
		assertEquals(validator.validate(new Opinion("Jay", STRING_150K_CHARS)).size(), 0);
		assertEquals(validator.validate(new Opinion(STRING_150K_CHARS, "Not much to say")).size(), 1);
		assertEquals(validator.validate(new Opinion(STRING_150K_CHARS, STRING_150K_CHARS)).size(), 1);

		// Undersized strings
		assertEquals(validator.validate(new Opinion("", "")).size(), 0);
		assertEquals(validator.validate(new Opinion("", "")).size(), 0);
		assertEquals(validator.validate(new Opinion("", "")).size(), 0);
	}
}
