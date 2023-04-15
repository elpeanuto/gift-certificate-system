package com.epam.esm.service.api;

import com.epam.esm.model.Entity;

public interface CRUDService<T extends Entity> extends CRDService<T>{

    T update(int id, T t);
}
