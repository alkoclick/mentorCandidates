package control;

import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Opinion;
import util.SessionBuilder;

@Controller
public class OpinionController {

	private static final String URI = "/opinion";

	@RequestMapping(method = RequestMethod.GET, value = { URI })
	ResponseEntity<?> getOpinion(@RequestParam(value = "id", defaultValue = "-1") long idOpinion) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		Opinion opinion = session.find(Opinion.class, idOpinion);
		session.getTransaction().commit();
		session.close();

		return opinion != null ? ResponseEntity.ok(opinion) : ResponseEntity.notFound().build();
	}
}
