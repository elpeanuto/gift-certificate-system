package com.epam.esm.repository;

import com.epam.esm.model.Entity;
import com.epam.esm.model.impl.Tag;

import java.util.List;

public interface GiftCertificateRepository<T extends Entity> extends CRUDRepository<T>{

    List<T> getByIdList(List<Integer> idList);
}
