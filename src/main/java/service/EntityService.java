package service;

import java.util.Collection;

public interface EntityService<T> {

	public void add(T t);

	public Collection<T> getAll();

	public T getById(long id);

	public void delete(T t);

}
