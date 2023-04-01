package com.epam.esm.service;

import com.epam.esm.model.Entity;

public interface CRUDService<T extends Entity> extends CRDService<T>{
    int update(int id, T t);
}
