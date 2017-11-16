package util;

import java.util.Arrays;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;

public class ValidationTest {

	protected static Validator validator;
	protected static String STRING_65K_CHARS;
	protected static String STRING_150K_CHARS;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		STRING_65K_CHARS = createStringWithSize(65000);
		STRING_150K_CHARS = createStringWithSize(15000);
	}

	private static String createStringWithSize(int length) {
		char[] chars = new char[length];
		Arrays.fill(chars, 't');
		return new String(chars);
	}
}
