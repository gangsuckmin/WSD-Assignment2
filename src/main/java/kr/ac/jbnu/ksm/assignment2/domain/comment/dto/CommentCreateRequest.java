package kr.ac.jbnu.ksm.assignment2.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
        @NotNull Integer reviewId,
        @NotNull Integer userId,
        @NotBlank String content
) {}