package com.epam.esm.model.dto.filter;

import java.util.Set;

/**
 * The GiftCertificateFilter class represents a filter for querying gift certificates
 * with pagination, tag filtering, and sorting options.
 */
public class GiftCertificateFilter extends Pagination {

    private final Set<String> tags;
    private final String partOfNameDescription;
    private final String sortOrder;

    /**
     * Constructs a GiftCertificateFilter object with the specified filter parameters.
     *
     * @param tags                  The set of tags to filter by.
     * @param partOfNameDescription A part of the name or description to search for.
     * @param sortOrder             The sorting order ("asc" or "desc").
     * @param page                  The page number (greater than 0).
     * @param limit                 The maximum number of items per page (between 1 and MAX_LIMIT).
     */
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
