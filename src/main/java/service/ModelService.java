package service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

import dao.ModelDAO;

@Service
public abstract class ModelService<T> implements EntityService<T> {

	protected ModelDAO<T> modelDAO;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void add(T t) {
		this.modelDAO.add(t);
	}

	@Override
	@Transactional
	public Collection<T> getAll() {
		return this.modelDAO.getAll();
	}

	@Override
	@Transactional
	public T getById(long id) {
		return this.modelDAO.getById(id);
	}

	@Override
	@Transactional
	public void delete(T t) {
		this.modelDAO.delete(t);
	}

}
