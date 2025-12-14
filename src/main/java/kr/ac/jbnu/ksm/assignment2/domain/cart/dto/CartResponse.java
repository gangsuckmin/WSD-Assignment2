package kr.ac.jbnu.ksm.assignment2.domain.cart.dto;

import java.util.List;

public record CartResponse(
        Integer cartId,
        List<CartItemResponse> items,
        Integer subtotal
) {}