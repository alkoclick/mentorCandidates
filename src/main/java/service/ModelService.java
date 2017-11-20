package service;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import dao.ModelDAO;

@Service
@Scope("prototype")
@Transactional
public class ModelService<T> implements JpaRepository<T, Long> {

	@Autowired
	protected ModelDAO<T> modelDAO;

	public void setModelClass(Class<T> modelClass) {
		modelDAO.setModelClass(modelClass);
	}

	public ModelDAO<T> getModelDAO() {
		return modelDAO;
	}

	@Override
	public void delete(T t) {
		this.modelDAO.delete(t);
	}

	@Override
	public List<T> findAll() {
		return modelDAO.findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		return modelDAO.findAll(sort);
	}

	@Override
	public List<T> findAll(Iterable<Long> ids) {
		return modelDAO.findAll(ids);
	}

	@Override
	public <S extends T> List<S> save(Iterable<S> entities) {
		return modelDAO.save(entities);
	}

	@Override
	public void flush() {
		modelDAO.flush();
	}

	@Override
	public <S extends T> S saveAndFlush(S entity) {
		return modelDAO.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<T> entities) {
		modelDAO.delete(entities);
	}

	@Override
	public void deleteAllInBatch() {
		modelDAO.deleteAllInBatch();
	}

	@Override
	public T getOne(Long id) {
		return modelDAO.getOne(id);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example) {
		return modelDAO.findAll(example);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return modelDAO.findAll(example, sort);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return modelDAO.findAll(pageable);
	}

	@Override
	public <S extends T> S save(S entity) throws PersistenceException {
		return modelDAO.save(entity);
	}

	@Override
	public T findOne(Long id) {
		return modelDAO.findOne(id);
	}

	@Override
	public boolean exists(Long id) {
		return modelDAO.exists(id);
	}

	@Override
	public long count() {
		return modelDAO.count();
	}

	@Override
	public void delete(Long id) {
		modelDAO.delete(id);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		modelDAO.delete(entities);
	}

	@Override
	public void deleteAll() {
		modelDAO.deleteAll();
	}

	@Override
	public <S extends T> S findOne(Example<S> example) {
		return modelDAO.findOne(example);
	}

	@Override
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		return modelDAO.findAll(example, pageable);
	}

	@Override
	public <S extends T> long count(Example<S> example) {
		return modelDAO.count(example);
	}

	@Override
	public <S extends T> boolean exists(Example<S> example) {
		return modelDAO.exists(example);
	}

}
