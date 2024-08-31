package com.dardan.movement.service.Impl;

import com.dardan.movement.exception.NotFoundException;
import com.dardan.movement.repository.GenericRepo;
import com.dardan.movement.service.CRUD;
import com.dardan.movement.util.Constants;

import java.lang.reflect.Method;
import java.util.List;

public abstract class CRUDImpl<T, ID> implements CRUD<T, ID> {

    protected abstract GenericRepo<T, ID> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) throws Exception {
        Class<?> clazz = t.getClass();
        String className = t.getClass().getSimpleName();
        String methodName = "setId" + className;
        Method setIdMethod = clazz.getMethod(methodName, id.getClass());
        setIdMethod.invoke(t, id);

        getRepo().findById(id).orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND + id));
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id).orElseThrow(()-> new NotFoundException(Constants.NOT_FOUND + id));
    }

    @Override
    public void delete(ID id) {
        getRepo().findById(id).orElseThrow(() -> new NotFoundException(Constants.NOT_FOUND + id));
        getRepo().deleteById(id);
    }
}
