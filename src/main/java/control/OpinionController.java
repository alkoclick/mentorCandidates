package control;

import java.io.IOException;
import java.util.Collection;

import org.hibernate.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Opinion;
import util.SessionBuilder;

@Controller
public class OpinionController {
	public static final String URI = "/opinion";
	private static final Object HTTP_EMPTYREQUEST = "Empty";
	private static final Object HTTP_BADREQUEST = "Bad";
	private ObjectMapper mapper = new ObjectMapper();

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
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();

		ResponseEntity<?> responseEntity;
		if (id != null) {
			Opinion opinion = session.find(Opinion.class, id);
			responseEntity = opinion != null ? ResponseEntity.ok(opinion) : ResponseEntity.notFound().build();
		} else {
			Collection<?> results = session
					.createNativeQuery("select * from " + Opinion.class.getSimpleName() + ";", Opinion.class)
					.getResultList();
			responseEntity = results != null ? ResponseEntity.ok(results) : ResponseEntity.notFound().build();
		}

		session.getTransaction().commit();
		session.close();
		return responseEntity;
	}

	/**
	 * The endpoint for post requests
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST, value = { URI })
	ResponseEntity<?> getOpinion(@RequestBody(required = true) String body) {
		if (body == null || body.isEmpty() || body.equals("{}"))
			return ResponseEntity.badRequest().body(HTTP_EMPTYREQUEST);

		Opinion opinion;
		try {
			opinion = mapper.readValue(body, Opinion.class);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(HTTP_BADREQUEST);
		}

		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(opinion);
		session.getTransaction().commit();
		session.close();

		return ResponseEntity.ok(opinion);
	}
}
