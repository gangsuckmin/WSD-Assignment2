package kr.ac.jbnu.ksm.assignment2.domain.health.dto;

import java.time.LocalDateTime;

public record HealthResponse(
        String status,
        String version,
        LocalDateTime serverTime
) {}