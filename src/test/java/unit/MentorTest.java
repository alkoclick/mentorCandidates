package unit;

import static org.junit.Assert.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Mentor;

public class MentorTest {

	protected static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void validateEmail() {
		Mentor mentor = new Mentor("Alex", "Pap", "alexWeirdEmail", "This guy's email doesn't even have @");
		assertEquals(validator.validate(mentor).size(), 1);
	}

	@Test
	public void validateNulls() {

	}

	@Test
	public void validateComplex() {

	}

	@Test
	public void validateCorrect() {

	}
}
