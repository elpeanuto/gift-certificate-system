package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.filter.Search;

import java.util.List;

public interface GiftCertificateService extends CRUDService<GiftCertificateDTO, Pagination>,
        Search<GiftCertificateDTO, GiftCertificateFilter> {

    List<GiftCertificateDTO> getByParams(String name, String part, String sort);
}
