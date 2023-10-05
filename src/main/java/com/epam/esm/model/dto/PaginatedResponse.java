package com.epam.esm.model.dto;

import java.util.List;

public class PaginatedResponse<T> {

    private List<T> responseList;
    private Long totalCount;

    public PaginatedResponse(List<T> responseList, Long totalCount) {
        this.responseList = responseList;
        this.totalCount = totalCount;
    }

    public List<T> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<T> responseList) {
        this.responseList = responseList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
