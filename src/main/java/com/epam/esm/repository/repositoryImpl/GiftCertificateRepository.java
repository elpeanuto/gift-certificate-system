package com.epam.esm.repository.repositoryImpl;

import com.epam.esm.model.modelImpl.GiftCertificate;
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

    public int create(GiftCertificate giftCertificate) {
        String sql = "INSERT INTO gift_certificate(name, description, price, duration) VALUES(?, ?, ?, ?)";

        return jdbcTemplate.update(sql, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration());
    }

    public int update(int id, GiftCertificate updatedGiftCertificate) {
        String sql = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=? WHERE id=?";

        return jdbcTemplate.update(sql, updatedGiftCertificate.getName(), updatedGiftCertificate.getDescription(),
                updatedGiftCertificate.getPrice(), updatedGiftCertificate.getDuration(), id);
    }

    public int delete(int id) {
        String sql = "DELETE FROM gift_certificate WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }

    private static class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
        @Override
        public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new GiftCertificate(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("duration"),
                    rs.getTimestamp("create_date").toLocalDateTime(),
                    rs.getTimestamp("last_update_date").toLocalDateTime());
        }
    }
}
