package com.epam.esm.service.services.api;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.filter.GiftCertificateFilter;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.service.filter.Search;

public interface GiftCertificateService extends CRUDService<GiftCertificateDTO, Pagination>,
        Search<GiftCertificateDTO, GiftCertificateFilter> {
}
