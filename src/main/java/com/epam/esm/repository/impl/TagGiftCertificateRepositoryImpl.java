package com.epam.esm.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagGiftCertificateRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagGiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createTagGiftCertificate(int giftCertificateId, int tagId) {
        String sql = "INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id) VALUES (?, ?)";

        return jdbcTemplate.update(sql, giftCertificateId, tagId);
    }

    public List<Integer> getAllTagsIdByGiftCertificate(int giftCertificateId) {
        String sql = "SELECT tag_id FROM gift_certificate_tag WHERE gift_certificate_id=?";

        return jdbcTemplate.queryForList(sql, Integer.class, giftCertificateId);
    }
}
