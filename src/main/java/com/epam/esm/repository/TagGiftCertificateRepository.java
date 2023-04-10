package com.epam.esm.repository;

import java.util.List;

public interface TagGiftCertificateRepository {

    int createTagGiftCertificate(int giftCertificateId, int tagId);

    List<Integer> getAllTagsIdByGiftCertificate(int giftCertificateId);
}
