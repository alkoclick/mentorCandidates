package implem.control;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.MentorController;
import model.Mentor;
import util.HibernateTest;
import util.MentorHelper;

public class MentorControllerTest extends HibernateTest {
	private static final String URI = MentorController.URI;
	public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;
	private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new MentorController()).build();
	private ObjectMapper mapper = new ObjectMapper();

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
		session.save(mentor);
		session.getTransaction().commit();

		String response = this.mockMvc.perform(get(URI + "/" + mentor.getId()).accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertNotNull(new JSONObject(response));
		// JSON from String to Object
		Mentor responseOpinion = mapper.readValue(response, Mentor.class);

		MentorHelper.testEquality(mentor, responseOpinion);
	}
}
