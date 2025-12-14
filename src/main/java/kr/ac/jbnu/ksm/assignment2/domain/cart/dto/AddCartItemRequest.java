package kr.ac.jbnu.ksm.assignment2.domain.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddCartItemRequest(
        @NotNull Integer bookId,
        @NotNull @Min(1) Integer quantity
) {}