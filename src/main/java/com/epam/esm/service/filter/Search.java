package com.epam.esm.service.filter;

import com.epam.esm.model.dto.PaginatedResponse;
import com.epam.esm.model.dto.filter.Pagination;

/**
 * Interface for searching via filter
 *
 * @param <T> DTO type
 * @param <V> Fiter type
 */
public interface Search<T, V extends Pagination> {
    /**
     * Filter method
     *
     * @param filter Filter data
     * @return List of searched DTO
     */
    PaginatedResponse<T> doSearch(V filter);
}