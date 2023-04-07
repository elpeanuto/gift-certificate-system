package com.epam.esm.service;

import com.epam.esm.model.Entity;

import java.util.List;

public interface CRDService<T extends Entity> {

    List<T> getAll();

    T getById(int id);

    T create(T t);

    int delete(int id);
}
