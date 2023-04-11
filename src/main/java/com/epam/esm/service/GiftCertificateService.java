package com.epam.esm.service;

import com.epam.esm.model.impl.GiftCertificate;

import java.util.List;

public interface GiftCertificateService<T extends GiftCertificate> extends CRUDService<T> {

    List<T> getByParams(String name, String part, String sort);
}
