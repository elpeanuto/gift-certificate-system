package com.epam.esm.repository.repositoryImpl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.model.modelImpl.GiftCertificate;
import com.epam.esm.repository.CRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepository implements CRUDRepository<GiftCertificate> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GiftCertificate> getAll() {
        String sql = "SELECT * FROM gift_certificate";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    public GiftCertificate getById(int id) {
        String sql = "SELECT * FROM gift_certificate WHERE id = ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps;
        }, new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findFirst().orElse(null);
    }

    public GiftCertificate create(GiftCertificate giftCertificate) {
        String sql = "INSERT INTO gift_certificate(name, description, price, duration) " +
                "VALUES(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setDouble(3, giftCertificate.getPrice());
            ps.setInt(4, giftCertificate.getDuration());
            return ps;
        }, keyHolder);

        if (rows < 1) {
            throw new RepositoryException("Failed to create giftCertificate.");
        }

        Map<String, Object> key = keyHolder.getKeys();

        if (key == null) {
            throw new RepositoryException("Failed to get id.");
        }

        giftCertificate.setId((int) key.get("id"));
        giftCertificate.setCreateDate(((Timestamp) key.get("create_date")).toLocalDateTime());
        giftCertificate.setLastUpdateDate(((Timestamp) key.get("last_update_date")).toLocalDateTime());

        return giftCertificate;
    }

    public GiftCertificate update(int id, GiftCertificate updatedGiftCertificate) {
        String sql = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, last_update_date=? WHERE id=?";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, updatedGiftCertificate.getName());
            ps.setString(2, updatedGiftCertificate.getDescription());
            ps.setDouble(3, updatedGiftCertificate.getPrice());
            ps.setInt(4, updatedGiftCertificate.getDuration());
            ps.setObject(5, updatedGiftCertificate.getLastUpdateDate());
            ps.setInt(6, id);
            return ps;
        }, keyHolder);

        if (rows < 1) {
            throw new RepositoryException("Failed to create giftCertificate.");
        }

        Map<String, Object> key = keyHolder.getKeys();

        if (key == null) {
            throw new RepositoryException("Failed to get id.");
        }

        updatedGiftCertificate.setId((int) key.get("id"));
        updatedGiftCertificate.setCreateDate(((Timestamp) key.get("create_date")).toLocalDateTime());

        return updatedGiftCertificate;
    }

    public int delete(int id) {
        String sql = "DELETE FROM gift_certificate WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
