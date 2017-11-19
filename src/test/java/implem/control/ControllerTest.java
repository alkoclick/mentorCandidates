package implem.control;

import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import util.HibernateTest;

public abstract class ControllerTest<T> extends HibernateTest<T> {
	public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;
	protected ObjectMapper mapper = new ObjectMapper();
	protected MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

}
