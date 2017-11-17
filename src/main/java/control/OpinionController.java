package control;

import java.util.Collection;

import org.hibernate.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Opinion;
import util.SessionBuilder;

@Controller
public class OpinionController {

	public static final String URI = "/opinion";

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET, value = {
			URI + "/{id}" })
	ResponseEntity<?> getOpinion(@PathVariable(value = "id") Long id) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		Opinion opinion = session.find(Opinion.class, id);
		session.getTransaction().commit();
		session.close();

		return opinion != null ? ResponseEntity.ok(opinion) : ResponseEntity.notFound().build();
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET, value = URI)
	ResponseEntity<?> getOpinions() {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();

		Collection<?> results = results = session
				.createNativeQuery("select * from " + Opinion.class.getSimpleName() + ";", Opinion.class)
				.getResultList();
		session.getTransaction().commit();
		session.close();
		return results != null ? ResponseEntity.ok(results) : ResponseEntity.notFound().build();
	}
}
