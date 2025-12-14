package kr.ac.jbnu.ksm.assignment2.domain.book.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record BookCreateRequest(
        @NotBlank @Size(min = 13, max = 13) String isbn13,
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(max = 50) String publisher,
        @NotNull LocalDate publishedDate,
        @NotNull @Min(0) Integer price,
        @NotBlank @Size(min = 3, max = 3) String currency,
        @NotNull @Min(0) Integer stock
) {}