package ru.abyssone.employeeworktime.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MapRepository<T> extends CrudRepository<T, Long> {
}
