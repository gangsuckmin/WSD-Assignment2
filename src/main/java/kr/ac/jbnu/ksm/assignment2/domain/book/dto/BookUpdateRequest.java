package kr.ac.jbnu.ksm.assignment2.domain.book.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record BookUpdateRequest(
        @Size(max = 255) String title,
        @Size(max = 50) String publisher,
        LocalDate publishedDate,
        @Min(0) Integer price,
        @Size(min = 3, max = 3) String currency,
        @Min(0) Integer stock
) {}