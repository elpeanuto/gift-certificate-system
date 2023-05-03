package com.epam.esm.model.filter;

public class Pagination {

    public final Integer DEFAULT_PAGE = 0;
    public final Integer DEFAULT_LIMIT = 5;
    public final Integer MAX_LIMIT = 10;

    protected Integer page;
    protected Integer limit;

    public Pagination(Integer page, Integer limit) {
        this.page = page != null ? page : DEFAULT_PAGE;
        this.limit = limit != null ? limit : DEFAULT_LIMIT;

        if(limit > MAX_LIMIT)
            this.limit = MAX_LIMIT;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
