package com.epam.esm.repository.impl;

import com.epam.esm.repository.api.TagGiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of TagGiftCertificateRepository interface for accessing the gift_certificate_tag table in the database.
 * Uses JdbcTemplate to interact with the database.
 *
 * @see TagGiftCertificateRepository
 */
@Repository
public class TagGiftCertificateRepositoryImpl implements TagGiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new TagGiftCertificateRepositoryImpl object with the provided JdbcTemplate.
     *
     * @param jdbcTemplate JdbcTemplate to use for interacting with the database
     */
    @Autowired
    public TagGiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates a new entry in the gift_certificate_tag table with the specified gift certificate ID and tag ID.
     *
     * @param certificateId ID of the gift certificate to associate with the tag
     * @param tagId         ID of the tag to associate with the gift certificate
     * @return the number of rows affected by the insert statement
     */
    @Override
    public int createTagGiftCertificate(long certificateId, long tagId) {
        String sql = "INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id) VALUES (?, ?)";

        return jdbcTemplate.update(sql, certificateId, tagId);
    }

    /**
     * Retrieves a list of all tag IDs associated with the gift certificate with the specified ID.
     *
     * @param certificateId ID of the gift certificate to retrieve tags for
     * @return List of Integer objects representing the IDs of tags associated with the gift certificate
     */
    @Override
    public List<Long> getAllTagsIdByGiftCertificate(long certificateId) {
        String sql = "SELECT tag_id FROM gift_certificate_tag WHERE gift_certificate_id=?";

        return jdbcTemplate.queryForList(sql, Long.class, certificateId);
    }

    /**
     * Retrieves a list of all gift certificate IDs associated with the tag with the specified ID.
     *
     * @param tagId ID of the tag to retrieve gift certificates for
     * @return List of Integer objects representing the IDs of gift certificates associated with the tag
     */
    @Override
    public List<Long> getAllCertificateIdByTag(long tagId) {
        String sql = "SELECT gift_certificate_id FROM gift_certificate_tag WHERE tag_id=?";

        return jdbcTemplate.queryForList(sql, Long.class, tagId);
    }
}
