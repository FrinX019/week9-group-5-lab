package com.fp.w9.service;

import com.fp.w9.entity.Order;
import com.fp.w9.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
