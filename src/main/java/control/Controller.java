package control;

import java.io.IOException;
import java.util.Collection;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import service.ModelService;
import util.Messages;

public abstract class Controller<K> {
	private static final String HTTP_EMPTYREQUEST = Messages.getString("HTTP.Empty");
	private static final String HTTP_BADREQUEST = Messages.getString("HTTP.Bad");
	ObjectMapper mapper = new ObjectMapper();

	protected ModelService<K> service;

	/**
	 * Will return one, or all of the records in the data
	 * 
	 * @param objClass
	 *            The class of the requested record(s)
	 * @param id
	 *            Optional, the id of a record to return
	 * @return A {@link ResponseEntity} containing: If the id is null, JSONArray of
	 *         all the records in the db. If the id was specified and exists, the
	 *         record matching it. Otherwise the ResponseEntity wraps the
	 *         appropriate error code.
	 */
	protected ResponseEntity<?> getResponseEntity(Class<K> objClass, Long id) {
		if (id != null) {
			K obj = service.findOne(id);
			return obj != null ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
		} else {
			Collection<?> results = service.findAll();
			return results != null ? ResponseEntity.ok(results) : ResponseEntity.notFound().build();
		}
	}

	/**
	 * Attempts to insert the given body in the db, after unmarshalling it to a POJO
	 * 
	 * @param objClass
	 *            The class of the given object
	 * @param body
	 *            A POJO, in JSON form
	 * @return {@link ResponseEntity} If insertion was successful, the inserted
	 *         record is returned. Otherwise the ResponseEntity wraps the
	 *         appropriate error code.
	 */
	protected ResponseEntity<?> postResponseEntity(Class<K> objClass, String body) {
		if (body == null || body.isEmpty() || body.equals(Messages.getString("JSON.Empty"))) //$NON-NLS-1$
			return ResponseEntity.badRequest().body(HTTP_EMPTYREQUEST);

		try {
			K obj = mapper.readValue(body, objClass);
			obj = service.save(obj);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(obj);
		} catch (IOException | PersistenceException e) {
			return ResponseEntity.badRequest().body(HTTP_BADREQUEST);
		}

	}

	public ModelService<K> getService() {
		return service;
	}

	public void setService(ModelService<K> service) {
		this.service = service;
	}
}
