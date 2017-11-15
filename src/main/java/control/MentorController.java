package control;

import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Mentor;
import util.SessionBuilder;

@Controller
public class MentorController {

	private static final String URI = "/mentor";

	@RequestMapping(method = RequestMethod.GET, value = { URI })
	ResponseEntity<?> getMentor(@RequestParam(value = "id", defaultValue = "-1") long idMentor) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		Mentor mentor = session.find(Mentor.class, idMentor);
		session.getTransaction().commit();
		session.close();

		return mentor != null ? ResponseEntity.ok(mentor) : ResponseEntity.notFound().build();
	}
}
