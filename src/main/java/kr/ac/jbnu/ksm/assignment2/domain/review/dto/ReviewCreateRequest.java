package kr.ac.jbnu.ksm.assignment2.domain.review.dto;

import jakarta.validation.constraints.*;

public record ReviewCreateRequest(
        @NotNull Integer bookId,
        @NotNull Integer userId,
        @NotNull @Min(1) @Max(5) Integer rating,
        @NotBlank String content
) {}