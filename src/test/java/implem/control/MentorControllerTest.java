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

import control.MentorController;
import model.Mentor;
import model.Opinion;
import util.MentorHelper;

public class MentorControllerTest extends ControllerTest<Mentor> {

	@BeforeClass
	public static void setURI() {
		URI = MentorController.URI;
	}

	@Before
	@Override
	public void setClass() {
		service.setModelClass(Mentor.class);
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
		Mentor mentor = new Mentor("Alex", "Pap", "a@b.com", "Alex is a Java mentor");
		mentor.addOpinion(new Opinion("Jay", "Amazing"));
		mentor = service.save(mentor);

		String response = this.mockMvc.perform(get(URI + "/" + mentor.getId()).accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertNotNull(new JSONObject(response));
		// JSON from String to Object
		Mentor responseMentor = mapper.readValue(response, Mentor.class);

		MentorHelper.testEquality(mentor, responseMentor);
		assertTrue(responseMentor.getOpinions().size() > 0);
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
		List<Mentor> mentors = service.save(IntStream.range(0, BATCH_SIZE)
				.mapToObj(i -> new Mentor("a", "b", "a@b.com", "Batch mentor")).collect(Collectors.toList()));

		String response = this.mockMvc.perform(get(URI).accept(CONTENT_TYPE)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();

		JSONArray responseMentors = new JSONArray(response);
		assertNotNull(responseMentors);
		assertNotEquals(responseMentors.length(), 0);

		for (int i = 0; i < responseMentors.length(); ++i) {
			MentorHelper.testEquality(mapper.readValue(responseMentors.getJSONObject(i).toString(), Mentor.class),
					mentors.get(i));
		}
	}

	@Test
	public void postRecordTest() throws Exception {
		Mentor mentor = new Mentor("Stan", "Lee", "stan@marvel.com", "Excelsior");
		mentor.addOpinion(new Opinion("Neil Gaiman", "Confusing"));

		String response = this.mockMvc
				.perform(post(URI).content(mapper.writeValueAsString(mentor)).accept(CONTENT_TYPE))
				.andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();

		assertNotEquals(response.length(), 0);
		assertNotNull(new JSONObject(response));

		Mentor postMentor = mapper.readValue(response, Mentor.class);
		Mentor dbMentor;
		assertNotNull(dbMentor = service.findOne(postMentor.getId()));
		MentorHelper.testEquality(postMentor, dbMentor);

		assertTrue(postMentor.getOpinions().size() > 0);
	}
}
