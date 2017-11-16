package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Mentor;
import util.ValidationTest;

public class MentorTest extends ValidationTest {

	/**
	 * Verifies that a single {@link model.Mentor} object is correctly instantiated
	 */
	@Test
	public void testObjectInstantiation() {
		String firstName, lastName, email, description;
		Mentor mentor = new Mentor(firstName = "Alex", lastName = "Pap", email = "alexPap@gmail.com",
				description = "AlexPap is a Java mentor");

		// Not loaded into DB yet so...
		assertTrue(mentor.getId() == 0);
		assertEquals(mentor.getOpinions().size(), 0);

		assertEquals(mentor.getFirstName(), firstName);
		assertEquals(mentor.getLastName(), lastName);
		assertEquals(mentor.getEmail(), email);
		assertEquals(mentor.getDescription(), description);
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
		assertEquals(validator.validate(new Mentor(null, " ", "a@b.me", " ")).size(), 1);
		assertEquals(validator.validate(new Mentor(" ", null, "a@b.me", " ")).size(), 1);
		assertEquals(validator.validate(new Mentor(" ", " ", null, " ")).size(), 1);

		// Ensure that nullable values don't throw validation errors
		assertEquals(validator.validate(new Mentor(" ", " ", "a@b.me", null)).size(), 0);

		// Ensure combinations of nulls report all errors
		assertEquals(validator.validate(new Mentor(null, null, null, null)).size(), 3);
		assertEquals(validator.validate(new Mentor(" ", " ", null, null)).size(), 1);
		assertEquals(validator.validate(new Mentor(null, " ", null, " ")).size(), 2);

		// Ensure empty string doesn't trigger NotNull
		assertEquals(validator.validate(new Mentor("", "", "a@b.me", null)).size(), 0);
	}

	/**
	 * Validate some "normal" entries with the expected input
	 */
	@Test
	public void validateCorrect() {
		assertEquals(validator.validate(new Mentor("Alex", "Papageorgiou", "alex@fantasymail.com",
				"Alex is responsible for mentoring junior java candidates")).size(), 0);
		assertEquals(validator.validate(new Mentor("Aron", "Isaac", "aron@fantasymail.com",
				"Aron is responsible for mentoring senior java candidates")).size(), 0);
		assertEquals(validator
				.validate(new Mentor("Jerzy", "New", "jerzy@fantasymail.com", "Jerzy manages the mentor stuff")).size(),
				0);
	}

	/**
	 * Validate some entries with higher complexity than the average case. The email
	 * field is not tested so heavily as it is validated extensively in
	 * {@link #validateEmail()}
	 */
	@Test
	public void validateComplex() {

		// Oversized strings
		assertEquals(validator.validate(new Mentor(STRING_65K_CHARS, " ", "a@b.me", " ")).size(), 1);
		assertEquals(validator.validate(new Mentor(STRING_150K_CHARS, " ", "a@b.me", " ")).size(), 1);
		assertEquals(validator.validate(new Mentor(STRING_150K_CHARS, STRING_150K_CHARS, "a@b.me", STRING_150K_CHARS))
				.size(), 2);
		assertEquals(validator.validate(new Mentor(" ", " ", "a@b.me", STRING_150K_CHARS)).size(), 0);

		// Undersized strings
		assertEquals(validator.validate(new Mentor("Alex", "", "a@b.me", "Token description")).size(), 0);
		assertEquals(validator.validate(new Mentor("", "Pap", "a@b.me", "Token description")).size(), 0);
	}

	/**
	 * Test email validation bean to ensure it complies with
	 * <a href="http://www.ietf.org/rfc/rfc2822.txt">RFC2822</a>
	 * 
	 * @see <a href=
	 *      "https://blogs.msdn.microsoft.com/testing123/2009/02/06/email-address-test-cases/">Microsoft
	 *      email test cases</a>
	 * 
	 */
	@Test
	public void validateEmail() {
		// Valid
		assertEquals(validator.validate(new Mentor("", "", "email@domain.com", "Valid email")).size(), 0);
		assertEquals(validator
				.validate(
						new Mentor("", "", "firstname.lastname@domain.com", "Email contains dot in the address field"))
				.size(), 0);
		assertEquals(validator
				.validate(new Mentor("", "", "email@subdomain.domain.com", "Email contains dot with subdomain")).size(),
				0);
		assertEquals(validator
				.validate(
						new Mentor("", "", "firstname+lastname@domain.com", "Plus sign is considered valid character"))
				.size(), 0);
		assertEquals(validator.validate(
				new Mentor("", "", "email@[123.123.123.123]", "Square bracket around IP address is considered valid"))
				.size(), 0);
		assertEquals(
				validator.validate(new Mentor("", "", "1234567890@domain.com", "Digits in address are valid")).size(),
				0);
		assertEquals(
				validator.validate(new Mentor("", "", "email@123.123.123.123", "Domain is valid IP address")).size(),
				0);
		assertEquals(
				validator.validate(new Mentor("", "", "email@domain-one.com", "Dash in domain name is valid")).size(),
				0);
		assertEquals(validator
				.validate(new Mentor("", "", "_______@domain.com", "Underscore in the address field is valid")).size(),
				0);
		assertEquals(validator.validate(new Mentor("", "", "email@domain.name", ".name is valid Top Level Domain name"))
				.size(), 0);
		assertEquals(
				validator
						.validate(new Mentor("", "", "email@domain.co.jp",
								"Dot in Top Level Domain name also considered valid (use co.jp as example here)"))
						.size(),
				0);
		assertEquals(validator
				.validate(new Mentor("", "", "firstname-lastname@domain.com", "Dash in address field is valid")).size(),
				0);

		// Should be accepted but hibernate email regex erroneously rejects
		assertEquals(validator
				.validate(new Mentor("", "", "\"email\"@domain.com", "Quotes around email is considered valid")).size(),
				1);

		// Invalid
		assertEquals(validator.validate(new Mentor("", "", "plainaddress", "Missing @ sign and domain")).size(), 1);
		assertEquals(validator.validate(new Mentor("", "", "#@%^%#$@#$@#.com", "Garbage")).size(), 1);
		assertEquals(validator.validate(new Mentor("", "", "@domain.com", "Missing username")).size(), 1);
		assertEquals(validator
				.validate(new Mentor("", "", "Joe Smith <email@domain.com>", "Encoded html within email is invalid"))
				.size(), 1);
		assertEquals(validator.validate(new Mentor("", "", "email.domain.com", "Missing @")).size(), 1);
		assertEquals(validator.validate(new Mentor("", "", "email@domain@domain.com", "Two @ sign")).size(), 1);
		assertEquals(validator
				.validate(new Mentor("", "", ".email@domain.com", "Leading dot in address is not allowed")).size(), 1);
		assertEquals(validator
				.validate(new Mentor("", "", "email.@domain.com", "Trailing dot in address is not allowed")).size(), 1);
		assertEquals(validator.validate(new Mentor("", "", "email..email@domain.com", "Multiple dots")).size(), 1);
		assertEquals(validator
				.validate(new Mentor("", "", "email@domain.com (Joe Smith)", "Text followed email is not allowed"))
				.size(), 1);
		assertEquals(validator
				.validate(new Mentor("", "", "email@domain..com", "Multiple dot in the domain portion is invalid"))
				.size(), 1);

		// Oversized and wrong
		assertEquals(validator.validate(new Mentor("", "", STRING_150K_CHARS, "Missing @ sign and domain")).size(), 2);

		// Should be rejected but hibernate accepts them
		assertEquals(validator.validate(new Mentor("", "", "あいうえお@domain.com", "Unicode char as address")).size(), 0);
		assertEquals(validator
				.validate(new Mentor("", "", "email@domain", "Missing top level domain (.com/.net/.org/etc)")).size(),
				0);
		assertEquals(validator
				.validate(new Mentor("", "", "email@-domain.com", "Leading dash in front of domain is invalid")).size(),
				0);
		assertEquals(validator.validate(new Mentor("", "", "email@domain.web", ".web is not a valid top level domain"))
				.size(), 0);
		assertEquals(validator.validate(new Mentor("", "", "email@111.222.333.44444", "Invalid IP format")).size(), 0);
	}
}
