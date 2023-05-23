package com.epam.esm.model.dto.filter;

public class Pagination {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_LIMIT = 5;
    private static final Integer MAX_LIMIT = 10;

    protected Integer page;
    protected Integer limit;

    public Pagination(Integer page, Integer limit) {
        this.page = page != null ? page : DEFAULT_PAGE;
        this.limit = limit != null ? Math.min(limit, MAX_LIMIT) : DEFAULT_LIMIT;
    }

    public Integer getPage() {
        return page != null ? page : DEFAULT_PAGE;
    }

    public Integer getLimit() {
        return limit != null ? Math.min(limit, MAX_LIMIT) : DEFAULT_LIMIT;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + getPage() +
                ", limit=" + getLimit() +
                '}';
    }
}
