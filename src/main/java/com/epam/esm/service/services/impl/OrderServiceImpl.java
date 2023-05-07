package com.epam.esm.service.services.impl;

import com.epam.esm.model.converter.OrderConverter;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.filter.Pagination;
import com.epam.esm.model.entity.OrderEntity;
import com.epam.esm.model.entity.UserEntity;
import com.epam.esm.repository.api.CRUDRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.services.api.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.model.converter.OrderConverter.toDto;
import static com.epam.esm.model.converter.OrderConverter.toEntity;

@Service
public class OrderServiceImpl implements CRUDService<OrderDTO, Pagination> {

    private final CRUDRepository<OrderEntity, Pagination> orderRepo;
    private final GiftCertificateRepositoryImpl certificateRepo;
    private final CRUDRepository<UserEntity, Pagination> userRepo;

    @Autowired
    public OrderServiceImpl(CRUDRepository<OrderEntity, Pagination> orderRepo, GiftCertificateRepositoryImpl certificateRepo, CRUDRepository<UserEntity, Pagination> userRepo) {
        this.orderRepo = orderRepo;
        this.certificateRepo = certificateRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<OrderDTO> getAll(Pagination pagination) {
        return orderRepo.getAll(pagination).stream()
                .map(OrderConverter::toDto)
                .toList();
    }

    @Override
    public OrderDTO getById(long id) {
        return toDto(orderRepo.getById(id));
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        orderDTO.setCreateDate(LocalDateTime.now());

        Long userId = orderDTO.getUser().getId();


        //todo

        return toDto(orderRepo.create(toEntity(orderDTO)));
    }

    @Override
    public OrderDTO delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderDTO update(long id, OrderDTO orderDTO) {
        throw new UnsupportedOperationException();
    }
}
