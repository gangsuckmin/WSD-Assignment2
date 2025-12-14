package kr.ac.jbnu.ksm.assignment2.domain.review.dto;

import jakarta.validation.constraints.*;

public record ReviewUpdateRequest(
        @Min(1) @Max(5) Integer rating,
        String content
) {}