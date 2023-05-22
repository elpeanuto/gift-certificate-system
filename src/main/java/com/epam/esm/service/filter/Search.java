package com.epam.esm.service.filter;

import com.epam.esm.model.dto.filter.Pagination;

import java.util.List;

public interface Search<T, V extends Pagination> {

    List<T> doSearch(V filter);
}