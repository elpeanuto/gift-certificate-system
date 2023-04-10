package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.CRUDRepository;
import com.epam.esm.repository.TagGiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class GiftCertificateServiceImpl implements CRUDService<GiftCertificate> {

    private final CRUDRepository<GiftCertificate> certificateRepo;
    private final TagRepository<Tag> tagRepo;
    private final TagGiftCertificateRepository tagCertificateRepo;

    @Autowired
    public GiftCertificateServiceImpl(CRUDRepository<GiftCertificate> certificateRepo,
                                      TagRepository<Tag> tagRepo, TagGiftCertificateRepository tagCertificateRepo) {
        this.certificateRepo = certificateRepo;
        this.tagRepo = tagRepo;
        this.tagCertificateRepo = tagCertificateRepo;
    }

    public List<GiftCertificate> getAll() {
        try {
            List<GiftCertificate> certificateList = certificateRepo.getAll();

            for (GiftCertificate giftCertificate : certificateList) {
                List<Integer> tagIdList = tagCertificateRepo.getAllTagsIdByGiftCertificate(giftCertificate.getId());
                giftCertificate.setTags(tagRepo.getByIdList(tagIdList));
            }

            return certificateList;
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getAll()", e));
        }
    }

    public GiftCertificate getById(int id) {
        try {
            GiftCertificate certificate;

            certificate = certificateRepo.getById(id);

            if (certificate == null)
                throw new ResourceNotFoundException(id);

            List<Integer> tagIdList = tagCertificateRepo.getAllTagsIdByGiftCertificate(id);
            certificate.setTags(tagRepo.getByIdList(tagIdList));

            return certificate;
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getById(int id)", e));
        }
    }

    @Transactional
    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        return certificate.getTags() == null ? createGiftCertificate(certificate) :
                createGiftCertificateWithTags(certificate);
    }

    private GiftCertificate createGiftCertificate(GiftCertificate certificate) {
        GiftCertificate result;

        try {
            result = certificateRepo.create(certificate);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(),
                    "create(GiftCertificate giftCertificate)", e));
        }

        return result;
    }

    private GiftCertificate createGiftCertificateWithTags(GiftCertificate certificate) {
        List<Tag> tags = certificate.getTags();

        List<Tag> tagsToCreate = tags.stream()
                .distinct()
                .filter(tag -> tagRepo.getByName(tag.getName()) == null)
                .toList();

        List<Tag> tagsToBind = tags.stream()
                .distinct()
                .filter(tag -> tagRepo.getByName(tag.getName()) != null)
                .toList();

        tagsToBind.forEach(tag -> tag.setId(tagRepo.getByName(tag.getName()).getId()));

        GiftCertificate certificateFromDB = createGiftCertificate(certificate);

        tagsToCreate.stream()
                .map(tagRepo::create)
                .forEach(tag -> {
                    tagCertificateRepo.createTagGiftCertificate(certificateFromDB.getId(), tag.getId());
                    tag.setId(tag.getId());
                });

        tagsToBind
                .forEach(tag -> tagCertificateRepo.createTagGiftCertificate(certificateFromDB.getId(), tag.getId()));

        return certificateFromDB;
    }

    @Override
    @Transactional
    public GiftCertificate update(int id, GiftCertificate certificate) {
        GiftCertificate certificateFromDB;

        try {
            certificateFromDB = getById(id);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "update(int id)", e));
        }

        if (certificateFromDB == null) {
            throw new ResourceNotFoundException(id);
        }

        List<Tag> fromDBTags = certificateFromDB.getTags();
        List<Tag> requestTags = certificate.getTags();

        List<Tag> tagsToSet = requestTags.stream()
                .distinct()
                .filter(tag -> !fromDBTags.contains(tag))
                .toList();

        if (certificate.getName() != null) {
            certificateFromDB.setName(certificate.getName());
        }
        if (certificate.getDescription() != null) {
            certificateFromDB.setDescription(certificate.getDescription());
        }
        if (certificate.getPrice() != null) {
            certificateFromDB.setPrice(certificate.getPrice());
        }
        if (certificate.getDuration() != null) {
            certificateFromDB.setDuration(certificate.getDuration());
        }
        if (certificate.getTags() != null) {
            certificateFromDB.setTags(tagsToSet);
        }

        certificateFromDB.setLastUpdateDate(LocalDateTime.now());

        return certificate.getTags() == null ?
                updateGiftCertificate(id, certificateFromDB) : updateGiftCertificateWithTags(id, certificateFromDB);
    }

    private GiftCertificate updateGiftCertificate(int id, GiftCertificate certificate) {
        GiftCertificate result;

        try {
            result = certificateRepo.update(id, certificate);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "update(int id)", e));
        }

        return result;
    }

    private GiftCertificate updateGiftCertificateWithTags(int id, GiftCertificate certificate) {
        List<Tag> tags = certificate.getTags();

        List<Tag> tagsToCreate = tags.stream()
                .filter(tag -> tagRepo.getByName(tag.getName()) == null)
                .distinct()
                .toList();

        List<Tag> tagsToBind = tags.stream()
                .filter(tag -> tagRepo.getByName(tag.getName()) != null)
                .distinct()
                .toList();

        tagsToBind.forEach(tag -> tag.setId(tagRepo.getByName(tag.getName()).getId()));

        GiftCertificate updateCertificate = updateGiftCertificate(id, certificate);

        tagsToCreate.stream()
                .map(tagRepo::create)
                .forEach(tag -> {
                    tagCertificateRepo.createTagGiftCertificate(updateCertificate.getId(), tag.getId());
                    tag.setId(tag.getId());
                });

        tagsToBind
                .forEach(tag -> tagCertificateRepo.createTagGiftCertificate(updateCertificate.getId(), tag.getId()));

        updateCertificate.setTags(tagRepo.getByIdList(tagCertificateRepo.getAllTagsIdByGiftCertificate(updateCertificate.getId())));

        return updateCertificate;

    }

    public int delete(int id) {
        int result;

        try {
            result = certificateRepo.delete(id);
        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "delete(int id)", e));
        }

        if (result < 1) {
            throw new ResourceNotFoundException(id);
        }

        return result;
    }
}
