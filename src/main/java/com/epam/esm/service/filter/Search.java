package com.epam.esm.service.filter;

import com.epam.esm.model.dto.filter.Pagination;

import java.util.List;

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
    List<T> doSearch(V filter);
}