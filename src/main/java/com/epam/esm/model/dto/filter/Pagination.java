package com.epam.esm.model.dto.filter;

/**
 * The Pagination class represents pagination parameters for querying a list of items.
 * It includes page number and limit, with default values and maximum limit constraints.
 */
public class Pagination {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_LIMIT = 5;
    private static final Integer MAX_LIMIT = 50;

    protected Integer page;
    protected Integer limit;

    /**
     * Constructs a Pagination object with the specified page and limit.
     *
     * @param page  The page number (greater than 0).
     * @param limit The maximum number of items per page (between 1 and MAX_LIMIT).
     */
    public Pagination(Integer page, Integer limit) {
        this.page = page != null && page > 0 ? page : DEFAULT_PAGE;
        this.limit = limit != null && limit > 0 ? Math.min(limit, MAX_LIMIT) : DEFAULT_LIMIT;
    }

    public Integer getPage() {
        return page != null && page > 0 ? page : DEFAULT_PAGE;
    }

    public Integer getLimit() {
        return limit != null && limit > 0 ? Math.min(limit, MAX_LIMIT) : DEFAULT_LIMIT;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + getPage() +
                ", limit=" + getLimit() +
                '}';
    }
}
