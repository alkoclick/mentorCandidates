package control;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Mentor;
import service.MentorService;

@Controller
public class MentorController extends control.Controller<Mentor> {

	public MentorController() {
		service = new MentorService();
	}

	public static final String URI = "/mentor";

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = { URI, URI + "/{id}" })
	ResponseEntity<?> getMentor(@PathVariable(value = "id", required = false) Long id) {
		return getResponseEntity(Mentor.class, id);
	}

	/**
	 * The endpoint for post requests
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST, value = { URI })
	ResponseEntity<?> postMentor(@RequestBody(required = true) String body) {
		return postResponseEntity(Mentor.class, body);
	}
}
