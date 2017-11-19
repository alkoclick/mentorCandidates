package service;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityService<T> extends JpaRepository<T, Long> {

	public void add(T t);

	public Collection<T> getAll();

	public T getById(long id);

	public void delete(T t);

}
