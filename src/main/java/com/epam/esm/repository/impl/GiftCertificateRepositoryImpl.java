package com.epam.esm.repository.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.repository.api.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link GiftCertificateRepository} interface that uses JdbcTemplate and NamedParameterJdbcTemplate
 * for accessing the database. Provides CRUD operations to work with GiftCertificate entities.
 *
 * @see GiftCertificateRepository
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository<GiftCertificate> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor for creating a new instance of the GiftCertificateRepositoryImpl class.
     *
     * @param jdbcTemplate JdbcTemplate object for accessing the database.
     */
    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Returns a list of all Gift Certificate objects in the database.
     *
     * @return List of all GiftCertificate objects
     */
    @Override
    public List<GiftCertificate> getAll() {
        String sql = "SELECT * FROM gift_certificate";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    /**
     * Returns the Gift Certificate object with the specified ID.
     *
     * @param id ID of the GiftCertificate to be returned
     * @return GiftCertificate object with the specified ID or null if no object is found
     */
    @Override
    public GiftCertificate getById(long id) {
        String sql = "SELECT * FROM gift_certificate WHERE id = ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);

            return ps;
        }, new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findFirst().orElse(null);
    }

    /**
     * Returns a list of Gift Certificate objects with IDs in the specified list.
     *
     * @param idList List of IDs of the GiftCertificate objects to be returned
     * @return List of GiftCertificate objects with the specified IDs or an empty list if no objects are found
     */
    @Override
    public List<GiftCertificate> getByIdList(List<Long> idList) {
        String sql = "SELECT * FROM gift_certificate WHERE id IN (:idList)";

        if (idList.isEmpty())
            return Collections.emptyList();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idList", idList);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    /**
     * Returns a list of Gift Certificate objects whose name or description contains the specified pattern.
     *
     * @param pattern String pattern to be searched for in the name or description fields of GiftCertificate objects
     * @return List of GiftCertificate objects whose name or description contains the specified pattern or an empty list if no objects are found
     */
    @Override
    public List<GiftCertificate> getByPartOfNameDescription(String pattern) {
        String sql = "SELECT * FROM gift_certificate WHERE name ILIKE ? OR description ILIKE ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + pattern + "%");
            ps.setString(2, "%" + pattern + "%");

            return ps;
        }, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    /**
     * Creates a new Gift Certificate object in the database with the specified properties.
     *
     * @param certificate GiftCertificate object to be created in the database
     * @return GiftCertificate object with the ID and create/update date fields set
     */
    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        String sql = "INSERT INTO gift_certificate(name, description, price, duration) " +
                "VALUES(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setDouble(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            return ps;
        }, keyHolder);

        if (rows < 1) {
            throw new RepositoryException("Failed to create giftCertificate.");
        }

        Map<String, Object> key = keyHolder.getKeys();

        if (key == null) {
            throw new RepositoryException("Failed to get id.");
        }

        certificate.setId((long) key.get("id"));
        certificate.setCreateDate(((Timestamp) key.get("create_date")).toLocalDateTime());
        certificate.setLastUpdateDate(((Timestamp) key.get("last_update_date")).toLocalDateTime());

        return certificate;
    }

    /**
     * Updates the GiftCertificate object with the specified ID in the database with the properties
     * of the provided updatedGiftCertificate object.
     *
     * @param id                     The ID of the GiftCertificate to be updated.
     * @param updatedGiftCertificate The updated GiftCertificate object with new properties.
     * @return The updated GiftCertificate object with the ID, create date and last update date fields set.
     * @throws RepositoryException If the update operation fails or if the ID does not exist.
     */
    @Override
    public GiftCertificate update(long id, GiftCertificate updatedGiftCertificate) {
        String sql = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, last_update_date=? WHERE id=?";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, updatedGiftCertificate.getName());
            ps.setString(2, updatedGiftCertificate.getDescription());
            ps.setDouble(3, updatedGiftCertificate.getPrice());
            ps.setInt(4, updatedGiftCertificate.getDuration());
            ps.setObject(5, updatedGiftCertificate.getLastUpdateDate());
            ps.setLong(6, id);
            return ps;
        }, keyHolder);

        if (rows < 1) {
            throw new RepositoryException("Failed to create giftCertificate.");
        }

        Map<String, Object> key = keyHolder.getKeys();

        if (key == null) {
            throw new RepositoryException("Failed to get id.");
        }

        updatedGiftCertificate.setId((long) key.get("id"));
        updatedGiftCertificate.setCreateDate(((Timestamp) key.get("create_date")).toLocalDateTime());

        return updatedGiftCertificate;
    }

    /**
     * Deletes the GiftCertificate object with the specified ID from the database.
     *
     * @param id The ID of the GiftCertificate to be deleted.
     * @return The number of rows affected by the delete operation.
     * @throws RepositoryException If the delete operation fails or if the ID does not exist.
     */
    @Override
    public int delete(long id) {
        String sql = "DELETE FROM gift_certificate WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
