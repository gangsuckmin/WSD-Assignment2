package kr.ac.jbnu.ksm.assignment2.web;

import kr.ac.jbnu.ksm.assignment2.domain.stats.dto.*;
import kr.ac.jbnu.ksm.assignment2.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController
{

    private final StatsService statsService;

    @GetMapping("/top-books")
    public List<TopBookResponse> topBooks() {
        return statsService.topBooks();
    }

    @GetMapping("/daily-reviews")
    public List<DailyReviewStatResponse> dailyReviews() {
        return statsService.dailyReviews();
    }
}