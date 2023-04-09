package com.epam.esm.repository.repositoryImpl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.model.modelImpl.Tag;
import com.epam.esm.repository.CRDRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository<Tag> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> getAll() {
        String sql = "SELECT * FROM tag";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Tag.class));
    }

    public Tag getById(int id) {
        String sql = "SELECT * FROM tag WHERE id = ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps;
        }, new BeanPropertyRowMapper<>(Tag.class)).stream().findFirst().orElse(null);
    }

    public Tag getByName(String name) {
        String sql = "SELECT * FROM tag WHERE name = ?";

        return jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);

            return ps;
        }, new BeanPropertyRowMapper<>(Tag.class)).stream().findFirst().orElse(null);
    }

    public List<Tag> getByIdList(List<Integer> idList) {
        return idList.stream()
                .map(this::getById)
                .toList();
    }

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

    public int delete(int id) {
        String sql = "DELETE FROM tag WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
