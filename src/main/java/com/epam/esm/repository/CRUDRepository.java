package com.epam.esm.repository;

import com.epam.esm.model.Entity;

public interface CRUDRepository<T extends Entity> extends CRDRepository<T>{
    int update(int id, T t);
}
