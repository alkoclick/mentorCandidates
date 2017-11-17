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

import control.OpinionController;
import model.Opinion;
import unit.OpinionTest;
import util.HibernateTest;

public class OpinionControllerTest extends HibernateTest {
	private static final String URI = OpinionController.URI;
	public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;
	private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new OpinionController()).build();
	private ObjectMapper mapper = new ObjectMapper();

	// Get all
	// Assert 200
	// Assert Array
	// Test behaviour when empty DB
	// Test behaviour with huge DB

	// Get existing single
	// Record exists, assert 200 and required features
	// Assert JSONObject
	@Test
	public void getRecordTest() throws Exception {
		Opinion opinion = new Opinion("Jay Controller", "Jay is an aspiring Java programmer");
		session.save(opinion);

		String response = this.mockMvc.perform(get(URI + "/" + opinion.getId()).accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertNotNull(new JSONObject(response));
		// JSON from String to Object
		Opinion responseOpinion = mapper.readValue(response, Opinion.class);

		OpinionTest.testEquality(opinion, responseOpinion);
	}

	// Get nonexistent single
	// Assert 404

}
