package com.epam.esm.repository.impl;

import com.epam.esm.repository.api.TagGiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagGiftCertificateRepositoryImpl implements TagGiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagGiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createTagGiftCertificate(int certificateId, int tagId) {
        String sql = "INSERT INTO gift_certificate_tag(gift_certificate_id, tag_id) VALUES (?, ?)";

        return jdbcTemplate.update(sql, certificateId, tagId);
    }

    @Override
    public List<Integer> getAllTagsIdByGiftCertificate(int certificateId) {
        String sql = "SELECT tag_id FROM gift_certificate_tag WHERE gift_certificate_id=?";

        return jdbcTemplate.queryForList(sql, Integer.class, certificateId);
    }

    @Override
    public List<Integer> getAllCertificateIdByTag(int tagId) {
        String sql = "SELECT gift_certificate_id FROM gift_certificate_tag WHERE tag_id=?";

        return jdbcTemplate.queryForList(sql, Integer.class, tagId);
    }
}
