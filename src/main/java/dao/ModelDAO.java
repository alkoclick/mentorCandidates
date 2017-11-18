package dao;

import java.util.Collection;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import util.SessionBuilder;

@Repository
public abstract class ModelDAO<T> implements DAO<T> {
	protected Class<T> tClass;

	@Override
	public void add(T m) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(m);
		session.getTransaction().commit();
	}

	@Override
	public Collection<T> getAll() {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		return session.createNativeQuery("select * from " + tClass.getSimpleName() + ";", tClass).getResultList();
	}

	@Override
	public T getById(long id) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		return session.find(tClass, id);
	}

	@Override
	public void delete(T m) {
		Session session = SessionBuilder.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(m);
	}

}
