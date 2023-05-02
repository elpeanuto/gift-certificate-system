package com.epam.esm.model.filtering;

public record Pagination(Integer page, Integer limit) {

    public Pagination(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }
}
