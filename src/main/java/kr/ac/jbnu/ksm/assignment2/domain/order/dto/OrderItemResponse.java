package kr.ac.jbnu.ksm.assignment2.domain.order.dto;

public record OrderItemResponse(
        Integer id,
        Integer bookId,
        String itemTitle,
        Integer itemPrice,
        Integer quantity,
        Integer lineTotal
) {}