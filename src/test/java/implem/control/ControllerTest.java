package implem.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import util.HibernateTest;

public abstract class ControllerTest<T> extends HibernateTest<T> {
	protected static String URI;
	public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;
	protected ObjectMapper mapper = new ObjectMapper();
	protected MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	/**
	 * 
	 * Tests the /{mentor,opinion}/id endpoint's behaviour, when the specified
	 * record does not exist
	 * 
	 * @throws Exception
	 *             When performing the mockMVC request encountered an issue
	 */
	@Test
	public void getNonexistentRecordTest() throws Exception {
		String response = this.mockMvc.perform(get(URI + "/" + 10).accept(CONTENT_TYPE))
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		assertNotNull(response);
		assertTrue(response.isEmpty());
	}

	/**
	 * Tests the /{mentor/opinion} endpoint's behaviour, when there are no records
	 * in the db
	 * 
	 * @throws Exception
	 *             When performing the mockMVC request encountered an issue
	 */
	@Test
	public void getAllRecordsEmpty() throws Exception {
		String response = this.mockMvc.perform(get(URI).accept(CONTENT_TYPE)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();

		JSONArray responses = new JSONArray(response);
		assertNotNull(responses);
		assertEquals(responses.length(), 0);
	}

}
