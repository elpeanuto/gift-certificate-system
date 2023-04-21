package com.epam.esm.repository.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.api.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the TagRepository interface that uses JdbcTemplate and NamedParameterJdbcTemplate to interact with the database.
 *
 * @see TagRepository
 */
@Repository
public class TagRepositoryImpl implements TagRepository<Tag> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructs a new instance of the TagRepositoryImpl class.
     *
     * @param jdbcTemplate the JdbcTemplate to be used for database interaction.
     * @param dataSource   the DataSource to be used for database interaction.
     */
    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Retrieves all tags from the database.
     *
     * @return a list of Tag objects.
     */
    @Override
    public List<Tag> getAll() {
        String sql = "SELECT * FROM tag";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class));
    }

    /**
     * Retrieves a tag from the database with the specified ID.
     *
     * @param id the ID of the tag to retrieve.
     * @return the Tag object with the specified ID, or null if not found.
     */
    @Override
    public Tag getById(int id) {
        String sql = "SELECT * FROM tag WHERE id = ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps;
        }, new BeanPropertyRowMapper<>(Tag.class)).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves a tag from the database with the specified name.
     *
     * @param name the name of the tag to retrieve.
     * @return the Tag object with the specified name, or null if not found.
     */
    @Override
    public Tag getByName(String name) {
        String sql = "SELECT * FROM tag WHERE LOWER(name) = LOWER(?)";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);

            return ps;
        }, new BeanPropertyRowMapper<>(Tag.class)).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves a list of tags from the database with the specified IDs.
     *
     * @param idList the list of IDs of the tags to retrieve.
     * @return a list of Tag objects with the specified IDs.
     */
    @Override
    public List<Tag> getByIdList(List<Integer> idList) {
        String sql = "SELECT * FROM tag WHERE id IN (:idList)";

        if (idList.isEmpty())
            return Collections.emptyList();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idList", idList);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Tag.class));
    }

    /**
     * Creates a new tag in the database.
     *
     * @param tag the Tag object to be created.
     * @return the Tag object with the ID field set.
     * @throws RepositoryException if the operation fails.
     */
    @Override
    public Tag create(Tag tag) {
        String sql = "INSERT INTO tag(name) VALUES(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        if (rows < 1) {
            throw new RepositoryException("Failed to create giftCertificate.");
        }

        Map<String, Object> key = keyHolder.getKeys();

        if (key == null) {
            throw new RepositoryException("Failed to get id.");
        }

        tag.setId((int) key.get("id"));

        return tag;
    }

    /**
     * Deletes a tag with the given ID from the database.
     *
     * @param id the ID of the tag to be deleted
     * @return the number of rows affected by the deletion (should be 1 if successful)
     */
    @Override
    public int delete(int id) {
        String sql = "DELETE FROM tag WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
