package kr.ac.jbnu.ksm.assignment2.domain.cart.dto;

public record CartItemResponse(
        Integer id,
        Integer bookId,
        Integer quantity,
        Integer price,
        Integer lineTotal
) {}