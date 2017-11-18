package control;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Opinion;

@Controller
public class OpinionController extends control.Controller<Opinion> {
	public static final String URI = "/opinion";

	/**
	 * The endpoint for get requests
	 * 
	 * @param id
	 *            Optional, the id of the record to return
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET, value = { URI,
			URI + "/{id}" })
	ResponseEntity<?> getOpinion(@PathVariable(value = "id", required = false) Long id) {
		return getResponseEntity(Opinion.class, id);
	}

	/**
	 * The endpoint for post requests
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST, value = { URI })
	ResponseEntity<?> postOpinion(@RequestBody(required = true) String body) {
		return postResponseEntity(Opinion.class, body);
	}
}
