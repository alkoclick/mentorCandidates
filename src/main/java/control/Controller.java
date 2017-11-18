package control;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import service.EntityService;

public class Controller<K> {
	private static final Object HTTP_EMPTYREQUEST = "Empty";
	private static final Object HTTP_BADREQUEST = "Bad";
	ObjectMapper mapper = new ObjectMapper();
	protected EntityService<K> service;

	/**
	 * 
	 * @param objClass
	 *            The class to look for in the db
	 * @param id
	 * 
	 * @return
	 */
	protected ResponseEntity<?> getResponseEntity(Class<K> objClass, Long id) {
		if (id != null) {
			K obj = service.getById(id);
			return obj != null ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
		} else {
			Collection<?> results = service.getAll();
			return results != null ? ResponseEntity.ok(results) : ResponseEntity.notFound().build();
		}
	}

	protected ResponseEntity<?> postResponseEntity(Class<K> objClass, String body) {
		if (body == null || body.isEmpty() || body.equals("{}"))
			return ResponseEntity.badRequest().body(HTTP_EMPTYREQUEST);

		try {
			K obj = mapper.readValue(body, objClass);
			service.add(obj);
			return ResponseEntity.ok(obj);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(HTTP_BADREQUEST);
		}

	}
}
