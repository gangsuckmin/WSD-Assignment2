package kr.ac.jbnu.ksm.assignment2.domain.order.dto;

import java.time.LocalDateTime;

public record OrderSummaryResponse(
        Integer id,
        String number,
        String status,
        String paymentStatus,
        Integer total,
        String currency,
        LocalDateTime createdAt
) {}