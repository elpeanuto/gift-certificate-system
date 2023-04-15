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

@Repository
public class TagRepositoryImpl implements TagRepository<Tag> {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Tag> getAll() {
        String sql = "SELECT * FROM tag";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag getById(int id) {
        String sql = "SELECT * FROM tag WHERE id = ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps;
        }, new BeanPropertyRowMapper<>(Tag.class)).stream().findFirst().orElse(null);
    }

    @Override
    public Tag getByName(String name) {
        String sql = "SELECT * FROM tag WHERE name = ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);

            return ps;
        }, new BeanPropertyRowMapper<>(Tag.class)).stream().findFirst().orElse(null);
    }

    @Override
    public List<Tag> getByIdList(List<Integer> idList) {
        String sql = "SELECT * FROM tag WHERE id IN (:idList)";

        if(idList.isEmpty())
            return Collections.emptyList();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idList", idList);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Tag.class));
    }

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

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM tag WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
