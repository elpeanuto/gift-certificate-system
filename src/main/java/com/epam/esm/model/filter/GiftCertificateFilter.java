package com.epam.esm.model.filter;

import java.util.Set;

public class GiftCertificateFilter extends Pagination {

    private final Set<String> tags;
    private final String partOfNameDescription;
    private final String sortOrder;

    public GiftCertificateFilter(Set<String> tags, String partOfNameDescription,
                                 String sortOrder, Integer page, Integer limit) {
        super(page, limit);
        this.tags = tags;
        this.partOfNameDescription = partOfNameDescription;
        this.sortOrder = sortOrder;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getPartOfNameDescription() {
        return partOfNameDescription;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return "GiftCertificateFilter{" +
                "tags=" + tags +
                ", partOfNameDescription='" + partOfNameDescription + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
