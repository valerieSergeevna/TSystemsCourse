package com.spring.webapp.service;

import com.spring.exception.DataBaseException;
import com.spring.exception.ServerException;

import java.util.List;

public abstract class AbstractUserService<T> {
    public abstract List<T> getAll() throws DataBaseException;

    public abstract T save(T userDTO) throws DataBaseException;

    public abstract void delete(int id) throws DataBaseException;

    public abstract T get(int id) throws DataBaseException;

    public abstract T getByUserName(String name) throws DataBaseException, ServerException;

    public abstract T update(T UserDTO) throws DataBaseException;
}
