package com.epam.esm.repository.repositoryImpl;

import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.model.modelImpl.Tag;
import com.epam.esm.repository.CRDRepository;
import com.epam.esm.repository.CRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TagGiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CRDRepository<Tag> tagRepository;
    private final CRUDRepository<GiftCertificate> giftCertificateRepository;

    @Autowired
    public TagGiftCertificateRepository(JdbcTemplate jdbcTemplate, CRDRepository<Tag> tagRepository,
                                        CRUDRepository<GiftCertificate> giftCertificateRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRepository = tagRepository;
        this.giftCertificateRepository = giftCertificateRepository;
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
