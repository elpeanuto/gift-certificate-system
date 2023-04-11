package com.epam.esm.repository;

import com.epam.esm.model.impl.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository<T extends GiftCertificate> extends CRUDRepository<T>{

    List<T> getByIdList(List<Integer> idList);

    List<T> getByPartOfNameDescription(String pattern);
}
