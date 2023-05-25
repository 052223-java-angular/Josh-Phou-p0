package com.revature.app.daos;

import java.util.List;

public interface ICrudDAO<T> {
    void save(T t);
    void update(String id);
    void delete(String id);
    T findById(String id);
    List<T> findAll();

}
