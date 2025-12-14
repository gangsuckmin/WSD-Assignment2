package kr.ac.jbnu.ksm.assignment2.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse(
        Integer id,
        String number,
        String status,
        String paymentStatus,
        Integer subtotal,
        Integer shipping,
        Integer discount,
        Integer total,
        String currency,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {}