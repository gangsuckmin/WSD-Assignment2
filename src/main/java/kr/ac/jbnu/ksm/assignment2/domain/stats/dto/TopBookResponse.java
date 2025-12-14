package kr.ac.jbnu.ksm.assignment2.domain.stats.dto;

public record TopBookResponse(
        Integer bookId,
        String title,
        Long reviewCount
) {}