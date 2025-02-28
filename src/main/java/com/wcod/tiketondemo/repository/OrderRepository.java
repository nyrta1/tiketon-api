package com.wcod.tiketondemo.repository;

import com.wcod.tiketondemo.data.models.Order;
import com.wcod.tiketondemo.data.models.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
    List<Order> findByStatus(OrderStatus status);

    Page<Order> findByUserId(UUID userId, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Page<Order> findAll(Pageable pageable);
}
