package com.epam.esm.service;

import com.epam.esm.model.Entity;

import java.util.List;

public interface GiftCertificateService<T extends Entity> extends CRUDService<T> {

    List<T> getByParams(String name);
}
