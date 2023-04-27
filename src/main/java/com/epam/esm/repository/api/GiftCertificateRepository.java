package com.epam.esm.repository.api;

import com.epam.esm.model.entity.GiftCertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificateEntity, Long> {

}
