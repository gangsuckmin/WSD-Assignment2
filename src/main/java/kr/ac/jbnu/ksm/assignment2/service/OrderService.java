package kr.ac.jbnu.ksm.assignment2.service;

import kr.ac.jbnu.ksm.assignment2.domain.order.dto.*;
import kr.ac.jbnu.ksm.assignment2.domain.order.entity.Order;
import kr.ac.jbnu.ksm.assignment2.repository.OrderItemRepository;
import kr.ac.jbnu.ksm.assignment2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService
{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public Page<OrderSummaryResponse> myOrders(Integer userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(o -> new OrderSummaryResponse(
                        o.getId(), o.getNumber(), o.getStatus(), o.getPaymentStatus(),
                        o.getTotal(), o.getCurrency(), o.getCreatedAt()
                ));
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse myOrderDetail(Integer userId, Integer orderId) {
        Order o = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));

        var items = orderItemRepository.findByOrderId(orderId).stream()
                .map(i -> new OrderItemResponse(
                        i.getId(),
                        i.getBookId(),
                        i.getItemTitle(),
                        i.getItemPrice(),
                        i.getQuantity(),
                        i.getItemPrice() * i.getQuantity()
                ))
                .toList();

        return new OrderDetailResponse(
                o.getId(), o.getNumber(), o.getStatus(), o.getPaymentStatus(),
                o.getSubtotal(), o.getShipping(), o.getDiscount(), o.getTotal(),
                o.getCurrency(), o.getCreatedAt(), items
        );
    }
}