package kr.ac.jbnu.ksm.assignment2.domain.stats.dto;

import java.time.LocalDate;

public record DailyReviewStatResponse(
        LocalDate date,
        Long reviewCount
) {}