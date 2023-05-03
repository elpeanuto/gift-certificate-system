package com.epam.esm.service.api;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.filter.GiftCertificateFilter;

import java.util.List;

public interface GiftCertificateService extends CRUDService<GiftCertificateDTO, GiftCertificateFilter> {

    List<GiftCertificateDTO> getByParams(String name, String part, String sort);
}
