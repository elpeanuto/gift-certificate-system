package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.api.TagGiftCertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {
    private final GiftCertificateRepository<GiftCertificate> certificateRepo;

    private final TagRepository<Tag> tagRepo;
    private final TagGiftCertificateRepository tagCertificateRepo;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository<GiftCertificate> certificateRepo,
                                      TagRepository<Tag> tagRepo, TagGiftCertificateRepository tagCertificateRepo) {
        this.certificateRepo = certificateRepo;
        this.tagRepo = tagRepo;
        this.tagCertificateRepo = tagCertificateRepo;
    }

    @Override
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

    @Override
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

    @Override
    public List<GiftCertificate> getByParams(String tagName, String part, String sort) {
        List<GiftCertificate> responseList = new ArrayList<>();

        if (tagName != null) {
            responseList.addAll(getByTagNameParam(tagName));
        }

        if (part != null) {
            String partLowerCase = part.toLowerCase();

            if (tagName != null)
                responseList = responseList.stream()
                        .filter(certificate -> certificate.getName().toLowerCase().contains(partLowerCase)
                                || certificate.getDescription().toLowerCase().contains(partLowerCase))
                        .collect(Collectors.toList());
            else
                responseList.addAll(getByPartParam(part));
        }

        if (sort != null) {
            if (part == null && tagName == null)
                responseList = getAll();
            sortByParam(responseList, sort);
        }

        return responseList;
    }

    private List<GiftCertificate> getByTagNameParam(String name) {
        List<GiftCertificate> responseList;

        Tag tag = tagRepo.getByName(name);

        if (tag == null) {
            return Collections.emptyList();
        }

        List<Integer> certificateIds = tagCertificateRepo.getAllCertificateIdByTag(tag.getId());

        if (certificateIds == null) {
            return Collections.emptyList();
        }

        responseList = certificateRepo.getByIdList(certificateIds);

        responseList
                .forEach(certificate -> certificate.setTags(tagRepo.getByIdList(tagCertificateRepo.getAllTagsIdByGiftCertificate(certificate.getId()))));

        return responseList;
    }

    private List<GiftCertificate> getByPartParam(String part) {
        List<GiftCertificate> responseList;

        responseList = certificateRepo.getByPartOfNameDescription(part);
        responseList
                .forEach(certificate -> certificate.setTags(tagRepo.getByIdList(tagCertificateRepo.getAllTagsIdByGiftCertificate(certificate.getId()))));

        return responseList;
    }

    private void sortByParam(List<GiftCertificate> list, String sort) {
        if (sort.equals("DESC"))
            list.sort(Collections.reverseOrder(new GiftCertificate()));
        else if (sort.equals("ASC"))
            list.sort(new GiftCertificate());
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
        List<Tag> tags = certificate.getTags().stream()
                .distinct()
                .toList();

        certificate.setTags(tags);

        List<Tag> tagsToCreate = tags.stream()
                .filter(tag -> tagRepo.getByName(tag.getName()) == null)
                .toList();

        List<Tag> tagsToBind = tags.stream()
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

    @Override
    public GiftCertificate delete(int id) {
        GiftCertificate result;

        try {
            result = getById(id);

            if (certificateRepo.delete(id) < 1) {
                throw new RepositoryException("Failed to delete gift certificate");
            }

        } catch (DataAccessException e) {
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "delete(int id)", e));
        }

        return result;
    }
}
