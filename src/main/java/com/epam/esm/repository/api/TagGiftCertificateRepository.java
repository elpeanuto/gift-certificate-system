package com.epam.esm.repository.api;

import java.util.List;

public interface TagGiftCertificateRepository {

    int createTagGiftCertificate(int certificateId, int tagId);

    List<Integer> getAllTagsIdByGiftCertificate(int certificateId);

    List<Integer> getAllCertificateIdByTag(int tagId);
}