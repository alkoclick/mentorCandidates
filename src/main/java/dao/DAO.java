package dao;

import java.util.Collection;

public interface DAO<T> {

	public void add(T t);

	public Collection<T> getAll();

	public T getById(long id);

	public void delete(T t);
}
