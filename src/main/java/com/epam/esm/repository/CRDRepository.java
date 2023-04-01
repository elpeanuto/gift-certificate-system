package com.epam.esm.repository;

import com.epam.esm.model.Entity;

import java.util.List;

public interface CRDRepository<T extends Entity> {
    List<T> getAll();

    T getById(int id);

    int create(T t);

    int delete(int id);
}
