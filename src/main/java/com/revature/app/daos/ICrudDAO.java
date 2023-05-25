package com.revature.app.daos;

import java.util.List;

public interface ICrudDAO<T> {

    void save();
    void update();
    void delete();
    T findById(String id);
    List<T> findAll();

}
