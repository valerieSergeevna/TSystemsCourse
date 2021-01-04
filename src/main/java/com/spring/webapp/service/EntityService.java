package com.spring.webapp.service;

import com.spring.webapp.dao.EntityDAO;
import com.spring.webapp.entity.Doctor;

import java.util.List;

public interface EntityService<T>{
    List<T> getAll();
    void save(T item);
    T get(int id);
}
