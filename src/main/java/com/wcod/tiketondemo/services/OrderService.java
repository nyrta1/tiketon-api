package com.wcod.tiketondemo.services;

import com.wcod.tiketondemo.data.dto.props.OrderRequestDTO;
import com.wcod.tiketondemo.data.models.Order;
import com.wcod.tiketondemo.data.models.OrderItem;
import com.wcod.tiketondemo.data.models.OrderStatus;
import com.wcod.tiketondemo.data.models.Ticket;
import com.wcod.tiketondemo.data.models.UserEntity;
import com.wcod.tiketondemo.repository.OrderItemRepository;
import com.wcod.tiketondemo.repository.OrderRepository;
import com.wcod.tiketondemo.repository.TicketRepository;
import com.wcod.tiketondemo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final OrderItemRepository orderItemRepository;

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Page<Order> getOrdersByUser(UUID userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public Order createOrder(UUID userID, OrderRequestDTO requestDTO) {
        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Ticket> tickets = ticketRepository.findAllById(requestDTO.getTicketIds());
        if (tickets.isEmpty() || tickets.size() != requestDTO.getTicketIds().size()) {
            throw new EntityNotFoundException("Some tickets were not found");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);

        Order finalOrder = order;
        List<OrderItem> orderItems = tickets.stream().map(ticket -> {
            OrderItem item = new OrderItem();
            item.setOrder(finalOrder);
            item.setTicket(ticket);
            return item;
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        return order;
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}
