package kr.ac.jbnu.ksm.assignment2.web;

import kr.ac.jbnu.ksm.assignment2.domain.health.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HealthController
{

    @GetMapping("/health")
    public HealthResponse health()
    {
        return new HealthResponse(
                "UP",
                "1.0.0",
                LocalDateTime.now()
        );
    }
}