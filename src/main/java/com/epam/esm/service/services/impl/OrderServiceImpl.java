package com.epam.esm.service.services.impl;

import com.epam.esm.exception.exceptions.ResourceNotFoundException;
import com.epam.esm.model.converter.OrderConverter;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.PaginatedResponse;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.services.api.CRUDService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.model.converter.OrderConverter.toDto;

/**
 * Implementation of the CRUDService interface for managing Order objects.
 * Provides methods for retrieving, creating, updating and deleting orders.
 *
 * @see CRUDService
 */
@Service
public class OrderServiceImpl implements CRUDService<OrderDTO, Pagination> {

    private final CRUDRepository<OrderEntity, Pagination> orderRepo;
    private final GiftCertificateRepositoryImpl certificateRepo;
    private final CRUDRepository<UserEntity, Pagination> userRepo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderServiceImpl(CRUDRepository<OrderEntity, Pagination> orderRepo, GiftCertificateRepositoryImpl certificateRepo, CRUDRepository<UserEntity, Pagination> userRepo) {
        this.orderRepo = orderRepo;
        this.certificateRepo = certificateRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public PaginatedResponse<OrderDTO> getAll(Pagination pagination) {
        List<OrderDTO> orderDTOS = orderRepo.getAll(pagination).stream()
                .map(OrderConverter::toDto)
                .toList();

        return new PaginatedResponse<>(orderDTOS, null);
    }

    @Override
    @Transactional
    public OrderDTO getById(long id) {
        OrderEntity entity = orderRepo.getById(id)
                .orElseThrow(() -> {
                    logger.error("Order with ID {} not found.", id);
                    throw new ResourceNotFoundException(id);
                });

        return toDto(entity);
    }

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        Long userId = orderDTO.getUser().getId();
        List<GiftCertificateDTO> dtoList = orderDTO.getCertificates();

        Set<GiftCertificateEntity> certificateEntities = dtoList.stream()
                .map(certificate -> certificateRepo.getById(certificate.getId())
                        .orElseThrow(() -> {
                            logger.error("Gift Certificate with ID {} not found.", certificate.getId());
                            return new ResourceNotFoundException(certificate.getId());
                        })).collect(Collectors.toSet());

        UserEntity userEntity = userRepo.getById(userId)
                .orElseThrow(() -> {
                    logger.error("User with ID {} not found.", userId);
                    return new ResourceNotFoundException(userId);
                });
        Double totalPrice = certificateEntities.stream().
                mapToDouble(GiftCertificateEntity::getPrice)
                .sum();

        OrderEntity orderEntity = new OrderEntity(
                null,
                userEntity,
                certificateEntities,
                LocalDateTime.now(),
                totalPrice
        );

        return toDto(orderRepo.create(orderEntity));
    }

    @Override
    @Transactional
    public OrderDTO delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public OrderDTO update(long id, OrderDTO orderDTO) {
        throw new UnsupportedOperationException();
    }
}
