package com.epam.esm.service.filter;

import com.epam.esm.model.dto.PaginatedResponse;
import com.epam.esm.model.dto.filter.Pagination;

import java.util.List;

public interface Search<T, V extends Pagination> {

    PaginatedResponse<T> doSearch(V filter);
}