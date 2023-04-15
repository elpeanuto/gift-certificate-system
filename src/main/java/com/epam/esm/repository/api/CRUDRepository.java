package com.epam.esm.repository.api;

import com.epam.esm.model.Entity;

public interface CRUDRepository<T extends Entity> extends CRDRepository<T>{

    T update(int id, T t);
}
