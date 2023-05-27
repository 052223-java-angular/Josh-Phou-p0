package com.revature.app.daos;

import java.util.List;
import java.util.Optional;

public interface ICrudDAO<T> {
    void save(T t);
    void update(String id);
    void delete(String id);
    Optional<T> findById(String id);
    List<T> findAll();

}
