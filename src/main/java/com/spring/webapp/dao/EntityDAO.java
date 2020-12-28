package com.spring.webapp.dao;

import com.spring.webapp.entity.Doctor;

import java.util.List;

public interface EntityDAO<T>{
    List<T> getAll();

    void save(T item);
}
