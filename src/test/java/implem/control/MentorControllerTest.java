package implem.control;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;

import control.MentorController;
import model.Mentor;
import util.MentorHelper;

public class MentorControllerTest extends ControllerTest<Mentor> {
	private static final String URI = MentorController.URI;

	/**
	 * 
	 * Adds a record to the db, then tests the /opinion/id to retrieve it and assert
	 * that insertion and retrieval result in the same object
	 * 
	 * @throws Exception
	 *             When performing the mockMVC request encountered an issue
	 */
	@Test
	public void getRecordTest() throws Exception {
		Mentor mentor = new Mentor("Alex", "Pap", "a@b.com", "Alex is a Java mentor");
		service.add(mentor);

		String response = this.mockMvc.perform(get(URI + "/" + mentor.getId()).accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertNotNull(new JSONObject(response));
		// JSON from String to Object
		Mentor responseOpinion = mapper.readValue(response, Mentor.class);

		MentorHelper.testEquality(mentor, responseOpinion);
	}
}
