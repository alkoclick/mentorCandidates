package dao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class ModelDAO<T> implements JpaRepository<T, Long> {

	private Class<T> modelClass;

	@PersistenceContext
	private EntityManager em;

	@Override
	public void delete(T m) {
		em.remove(m);
	}

	@Override
	public List<T> findAll() {
		return em.createNativeQuery("select * from " + modelClass.getSimpleName(), modelClass).getResultList();
	}

	@Override
	public List<T> findAll(Sort sort) {
		throw new NotYetImplementedException();
	}

	@Override
	public List<T> findAll(Iterable<Long> ids) {
		throw new NotYetImplementedException();
	}

	@Override
	public <S extends T> List<S> save(Iterable<S> entities) {
		return StreamSupport.stream(entities.spliterator(), false).map(this::save).collect(Collectors.toList());
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public <S extends T> S saveAndFlush(S entity) {
		S entityCopy = save(entity);
		flush();
		return entityCopy;
	}

	@Override
	public void deleteInBatch(Iterable<T> entities) {
		throw new NotYetImplementedException();
	}

	@Override
	public void deleteAllInBatch() {
		throw new NotYetImplementedException();
	}

	public T getOne(Long id) {
		return em.find(modelClass, id);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example) {
		throw new NotYetImplementedException();
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		throw new NotYetImplementedException();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		throw new NotYetImplementedException();
	}

	@Override
	public <S extends T> S save(S entity) throws PersistenceException {
		em.persist(entity);
		return entity;
	}

	@Override
	public T findOne(Long id) {
		return em.find(modelClass, id);
	}

	@Override
	public boolean exists(Long id) {
		return findOne(id) != null;
	}

	@Override
	public long count() {
		return (long) em.createQuery("select count(1) from  " + modelClass.getSimpleName()).getSingleResult();
	}

	@Override
	public void delete(Long id) {
		T t = findOne(id);
		em.remove(t);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		entities.forEach(em::remove);
	}

	// You may want to switch this to iterative deletion to properly trigger
	// cascades
	// See https://stackoverflow.com/questions/3492453/hibernate-and-delete-all
	@Override
	public void deleteAll() {
		em.createQuery("DELETE FROM " + modelClass.getSimpleName());
	}

	@Override
	public <S extends T> S findOne(Example<S> example) {
		throw new NotYetImplementedException();
	}

	@Override
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		throw new NotYetImplementedException();
	}

	@Override
	public <S extends T> long count(Example<S> example) {
		throw new NotYetImplementedException();
	}

	@Override
	public <S extends T> boolean exists(Example<S> example) {
		throw new NotYetImplementedException();
	}

	public void setModelClass(Class<T> modelClass) {
		this.modelClass = modelClass;
	}

}
