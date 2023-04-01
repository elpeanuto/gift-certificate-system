package com.epam.esm.repository.repositoryImpl;

import com.epam.esm.model.modelImpl.Tag;
import com.epam.esm.repository.CRDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TagRepository implements CRDRepository<Tag> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepository(JdbcTemplate jdbcTemplate) {
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

    public int create(Tag tag) {
        String sql = "INSERT INTO tag(name) VALUES(?)";

        return jdbcTemplate.update(sql, tag.getName());
    }

    public int delete(int id) {
        String sql = "DELETE FROM gift_certificate WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }

}
