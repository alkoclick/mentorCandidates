package implem.control;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import control.OpinionController;
import model.Opinion;
import util.OpinionHelper;

public class OpinionControllerTest extends ControllerTest<Opinion> {

	@BeforeClass
	public static void setURI() {
		URI = OpinionController.URI;
	}

	@Before
	@Override
	public void setClass() {
		service.setModelClass(Opinion.class);
	}

	/**
	 * Adds a number of opinion records to the db, then tests the /opinions
	 * endpoint, getting all opinions. The returned object should be a JSONArray
	 * 
	 * @throws Exception
	 *             When performing the mockMVC request encountered an issue
	 */
	@Test
	public void getAllRecords() throws Exception {
		List<Opinion> opinions = service.save(IntStream.range(0, BATCH_SIZE)
				.mapToObj(i -> new Opinion("Student B", "B is for batch")).collect(Collectors.toList()));

		String response = this.mockMvc.perform(get(URI).accept(CONTENT_TYPE)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();

		JSONArray responseOpinions = new JSONArray(response);
		assertNotNull(responseOpinions);
		assertNotEquals(responseOpinions.length(), 0);

		for (int i = 0; i < responseOpinions.length(); ++i) {
			OpinionHelper.testEquality(mapper.readValue(responseOpinions.getJSONObject(i).toString(), Opinion.class),
					opinions.get(i));
		}

		service.delete(opinions);
	}

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
		Opinion opinion = new Opinion("Jay Controller", "Jay is an aspiring Java programmer");
		service.save(opinion);

		String response = this.mockMvc.perform(get(URI + "/" + opinion.getId()).accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertNotNull(new JSONObject(response));
		// JSON from String to Object
		Opinion responseOpinion = mapper.readValue(response, Opinion.class);

		OpinionHelper.testEquality(opinion, responseOpinion);
	}

	@Test
	public void postRecordTest() throws Exception {
		Opinion opinion = new Opinion("Jay", "Excelsior");
		assertTrue(this.mockMvc != null);
		String response = this.mockMvc
				.perform(post(URI).content(mapper.writeValueAsString(opinion)).accept(CONTENT_TYPE))
				.andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();

		assertNotNull(new JSONObject(response));
		assertNotEquals(response.length(), 0);

		Opinion postOpinion = mapper.readValue(response, Opinion.class);
		Opinion dbOpinion;
		assertNotNull(dbOpinion = service.findOne(postOpinion.getId()));

		service.delete(dbOpinion);
	}
}
