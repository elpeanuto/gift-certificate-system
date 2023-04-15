package com.epam.esm.repository.api;

import com.epam.esm.model.Entity;

import java.util.List;

public interface CRDRepository<T extends Entity> {

    List<T> getAll();

    T getById(int id);

    T create(T t);

    int delete(int id);
}
