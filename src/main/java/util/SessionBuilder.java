package util;

import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import model.Mentor;
import model.Opinion;

public class SessionBuilder {

	private static SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(SessionBuilder.class.getName());

	static {
		// configures settings from hibernate.cfg.xml
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
		try {
			sessionFactory = new MetadataSources(registry).addAnnotatedClass(Opinion.class)
					.addAnnotatedClass(Mentor.class).buildMetadata().buildSessionFactory();
			logger.info("Hibernate session factory loaded");
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble
			// building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
