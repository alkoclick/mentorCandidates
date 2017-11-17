package implem.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.OpinionController;
import implem.persist.OpinionPersistenceTests;
import model.Opinion;
import unit.OpinionTest;
import util.HibernateTest;

@Rollback
public class OpinionControllerTest extends HibernateTest {
	private static final String URI = OpinionController.URI;
	public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;
	private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new OpinionController()).build();
	private ObjectMapper mapper = new ObjectMapper();

	// Get all
	// Assert 200
	// Assert Array
	// Test behaviour when empty DB
	@Test
	public void getAllRecords() throws Exception {
		List<Opinion> opinions = OpinionPersistenceTests.addOpinionBatch(session);
		session.getTransaction().commit();

		String response = this.mockMvc.perform(get(URI).accept(CONTENT_TYPE)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();

		JSONArray responseOpinions = new JSONArray(response);
		assertNotNull(responseOpinions);
		assertNotEquals(responseOpinions.length(), 0);

		for (int i = 0; i < responseOpinions.length(); ++i) {
			OpinionTest.testEquality(mapper.readValue(responseOpinions.getJSONObject(i).toString(), Opinion.class),
					opinions.get(i));
		}

		session.beginTransaction();
		opinions.forEach(op -> session.delete(op));
		session.getTransaction().commit();
	}

	@Test
	public void getAllRecordsEmpty() throws Exception {
		createSchema();
		String response = this.mockMvc.perform(get(URI).accept(CONTENT_TYPE)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();

		JSONArray responseOpinions = new JSONArray(response);
		assertNotNull(responseOpinions);
		assertEquals(responseOpinions.length(), 0);
	}

	// Get existing single
	// Record exists, assert 200 and required features
	// Assert JSONObject
	@Test
	public void getRecordTest() throws Exception {
		Opinion opinion = new Opinion("Jay Controller", "Jay is an aspiring Java programmer");
		session.save(opinion);
		session.getTransaction().commit();

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
