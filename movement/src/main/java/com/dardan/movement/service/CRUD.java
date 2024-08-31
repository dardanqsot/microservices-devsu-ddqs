package com.dardan.movement.service;

import java.util.List;

public interface CRUD<T, ID> {

    T save(T t);
    T update(T t, ID id) throws Exception ;
    List<T> findAll();
    T findById(ID id);
    void delete(ID id);

}
