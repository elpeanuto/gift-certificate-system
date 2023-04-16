package com.epam.esm.service.impl;

import com.epam.esm.exception.exceptions.RepositoryException;
import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.repository.api.GiftCertificateRepository;
import com.epam.esm.repository.api.TagGiftCertificateRepository;
import com.epam.esm.repository.api.TagRepository;
import com.epam.esm.service.api.GiftCertificateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides implementation of GiftCertificateService interface.
 * It performs operations related to GiftCertificate, using GiftCertificateRepository, TagRepository and TagGiftCertificateRepository.
 * It also implements the methods of GiftCertificateService interface.
 *
 * @see GiftCertificateService
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {

    private final Logger logger = Logger.getLogger(this.getClass());
    private GiftCertificateRepository<GiftCertificate> certificateRepo;
    private TagRepository<Tag> tagRepo;
    private TagGiftCertificateRepository tagCertificateRepo;

    /**
     * Creates a new instance of GiftCertificateServiceImpl.
     */
    public GiftCertificateServiceImpl() {

    }

    /**
     * Creates a new instance of GiftCertificateServiceImpl.
     *
     * @param certificateRepo    the object that will be used to perform CRUD operations on GiftCertificates.
     * @param tagRepo            the object that will be used to perform CRUD operations on Tags.
     * @param tagCertificateRepo the object that will be used to perform CRUD operations on TagGiftCertificates.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository<GiftCertificate> certificateRepo,
                                      TagRepository<Tag> tagRepo, TagGiftCertificateRepository tagCertificateRepo) {
        this.certificateRepo = certificateRepo;
        this.tagRepo = tagRepo;
        this.tagCertificateRepo = tagCertificateRepo;
    }

    /**
     * Retrieves all the GiftCertificates from the database.
     *
     * @return a List containing all the GiftCertificates from the database.
     * @throws RepositoryException if there was an error accessing the database.
     */
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
            logger.error(e);
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getAll()", e));
        }
    }

    /**
     * Retrieves a GiftCertificate by its id.
     *
     * @param id the id of the GiftCertificate to retrieve.
     * @return the GiftCertificate with the specified id.
     * @throws RepositoryException       if there was an error accessing the database.
     * @throws ResourceNotFoundException if the GiftCertificate was not found in the database.
     */
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
            logger.error(e);
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "getById(int id)", e));
        }
    }

    /**
     * Returns a list of gift certificates that meet the specified parameters.
     *
     * @param tagName the name of the tag
     * @param part    a substring of the gift certificate's name or description
     * @param sort    the way to sort the list of gift certificates ("ASC" or "DESC")
     * @return the list of gift certificates that meet the specified parameters
     */
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

    /**
     * Creates a new gift certificate.
     *
     * @param certificate the gift certificate to be created
     * @return the created gift certificate
     */
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
            logger.error(e);
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

    /**
     * Updates an existing gift certificate.
     *
     * @param id          the id of the gift certificate to be updated
     * @param certificate the updated gift certificate
     * @return the updated gift certificate
     */
    @Override
    @Transactional
    public GiftCertificate update(int id, GiftCertificate certificate) {
        GiftCertificate certificateFromDB;

        try {
            certificateFromDB = getById(id);
        } catch (DataAccessException e) {
            logger.error(e);
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
            logger.error(e);
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

    /**
     * Deletes the gift certificate with the specified ID.
     *
     * @param id the ID of the gift certificate to delete
     * @return the deleted gift certificate
     * @throws RepositoryException if there was an error deleting the gift certificate from the repository
     */
    @Override
    public GiftCertificate delete(int id) {
        GiftCertificate result;

        try {
            result = getById(id);

            if (certificateRepo.delete(id) < 1) {
                throw new RepositoryException("Failed to delete gift certificate");
            }

        } catch (DataAccessException e) {
            logger.error(e);
            throw new RepositoryException(RepositoryException.standardMessage(this.getClass().getSimpleName(), "delete(int id)", e));
        }

        return result;
    }
}
