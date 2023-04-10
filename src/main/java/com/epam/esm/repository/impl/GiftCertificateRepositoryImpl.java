package com.epam.esm.repository.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.CRUDRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public class GiftCertificateRepositoryImpl implements CRUDRepository<GiftCertificate> {

    private final JdbcTemplate jdbcTemplate;
    private final TagRepository<Tag> tagRepository;
    private TagGiftCertificateRepositoryImpl tagGiftCertificateRepositoryImpl;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, TagRepository<Tag> tagRepository, PlatformTransactionManager transactionManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRepository = tagRepository;
        this.transactionManager = transactionManager;
    }

    @Autowired
    public void setTagGiftCertificateRepository(TagGiftCertificateRepositoryImpl tagGiftCertificateRepositoryImpl) {
        this.tagGiftCertificateRepositoryImpl = tagGiftCertificateRepositoryImpl;
    }

    @Override
    public List<GiftCertificate> getAll() {
        String sql = "SELECT * FROM gift_certificate";

        List<GiftCertificate> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GiftCertificate.class));

        for (GiftCertificate giftCertificate : list) {
            List<Integer> tagIdList = tagGiftCertificateRepositoryImpl.getAllTagsIdByGiftCertificate(giftCertificate.getId());
            giftCertificate.setTags(tagRepository.getByIdList(tagIdList));
        }

        return list;
    }

    @Override
    public GiftCertificate getById(int id) {
        String sql = "SELECT * FROM gift_certificate WHERE id = ?";

        GiftCertificate giftCertificate = jdbcTemplate.query(con -> {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps;
        }, new BeanPropertyRowMapper<>(GiftCertificate.class)).stream().findFirst().orElse(null);

        if (giftCertificate == null)
            throw new ResourceNotFoundException(id);

        List<Integer> tagIdList = tagGiftCertificateRepositoryImpl.getAllTagsIdByGiftCertificate(id);
        giftCertificate.setTags(tagRepository.getByIdList(tagIdList));

        return giftCertificate;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        return giftCertificate.getTags() == null ?
                createGiftCertificate(giftCertificate) : createGiftCertificateWithTag(giftCertificate);
    }

    private GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
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

    private GiftCertificate createGiftCertificateWithTag(GiftCertificate giftCertificate) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        List<Tag> tags = giftCertificate.getTags();

        List<Tag> tagsToCreate = tags.stream()
                .distinct()
                .filter(tag -> tagRepository.getByName(tag.getName()) == null)
                .toList();

        List<Tag> tagsToBind = tags.stream()
                .distinct()
                .filter(tag -> tagRepository.getByName(tag.getName()) != null)
                .toList();

        tagsToBind.forEach(tag -> tag.setId(tagRepository.getByName(tag.getName()).getId()));


        return transactionTemplate.execute(status -> {

            GiftCertificate certificate = createGiftCertificate(giftCertificate);

            tagsToCreate.stream()
                    .map(tagRepository::create)
                    .forEach(tag -> {
                        tagGiftCertificateRepositoryImpl.createTagGiftCertificate(certificate.getId(), tag.getId());
                        tag.setId(tag.getId());
                    });

            tagsToBind
                    .forEach(tag -> tagGiftCertificateRepositoryImpl.createTagGiftCertificate(certificate.getId(), tag.getId()));

            return certificate;
        });
    }

    @Override
    public GiftCertificate update(int id, GiftCertificate updatedGiftCertificate) {
        return updatedGiftCertificate.getTags() == null ?
                updateGiftCertificate(id, updatedGiftCertificate) : updateGiftCertificateWithTags(id, updatedGiftCertificate);
    }

    private GiftCertificate updateGiftCertificate(int id, GiftCertificate updatedGiftCertificate) {
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

    private GiftCertificate updateGiftCertificateWithTags(int id, GiftCertificate giftCertificate) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        List<Tag> tags = giftCertificate.getTags();

        List<Tag> tagsToCreate = tags.stream()
                .filter(tag -> tagRepository.getByName(tag.getName()) == null)
                .distinct()
                .toList();

        List<Tag> tagsToBind = tags.stream()
                .filter(tag -> tagRepository.getByName(tag.getName()) != null)
                .distinct()
                .toList();

        tagsToBind.forEach(tag -> tag.setId(tagRepository.getByName(tag.getName()).getId()));


        return transactionTemplate.execute(status -> {
            GiftCertificate certificate = updateGiftCertificate(id, giftCertificate);

            tagsToCreate.stream()
                    .map(tagRepository::create)
                    .forEach(tag -> {
                        tagGiftCertificateRepositoryImpl.createTagGiftCertificate(certificate.getId(), tag.getId());
                        tag.setId(tag.getId());
                    });

            tagsToBind
                    .forEach(tag -> tagGiftCertificateRepositoryImpl.createTagGiftCertificate(certificate.getId(), tag.getId()));

            certificate.setTags(tagRepository.getByIdList(tagGiftCertificateRepositoryImpl.getAllTagsIdByGiftCertificate(certificate.getId())));

            return certificate;
        });
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM gift_certificate WHERE id=?";

        return jdbcTemplate.update(sql, id);
    }
}
