package control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Mentor;
import service.ModelService;

@Controller
public class MentorController extends control.Controller<Mentor> {

	public static final String URI = "/mentor";

	@Override
	@Autowired
	public void setService(ModelService<Mentor> service) {
		super.setService(service);
		service.setModelClass(Mentor.class);
	}

	/**
	 * The mapper for GET requests in the /mentor/{id} endpoint.
	 * 
	 * Full documentation available in the markdown file:
	 * docs/api/endpoints/mentors.md
	 * 
	 * @param id
	 *            Optional, the id of the record to return
	 * @return {@link ResponseEntity}, containing: A JSONArray, if no id was
	 *         specified, or a JSONObject if the id was specified and found in the
	 *         db. In any other case, it will be wrapping the appropriate error
	 *         code.
	 * @see docs/api/entities/mentor.md
	 */
	@RequestMapping(method = RequestMethod.GET, value = { URI, URI + "/{id}" })
	ResponseEntity<?> getMentor(@PathVariable(value = "id", required = false) Long id) {
		return getResponseEntity(Mentor.class, id);
	}

	/**
	 * The mapper for POST requests in the /mentor endpoint.
	 * 
	 * Full documentation available in the markdown file:
	 * docs/api/endpoints/mentors.md
	 * 
	 * @param body
	 *            An {@link mentor} object, in JSON
	 * @return {@link ResponseEntity} containing a JSONObject if the insertion in
	 *         the db was successful. In any other case, it will be wrapping the
	 *         appropriate error code.
	 * @see docs/api/entities/mentor.md
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST, value = { URI })
	ResponseEntity<?> postMentor(@RequestBody(required = true) String body) {
		return postResponseEntity(Mentor.class, body);
	}
}
