package kr.ac.jbnu.ksm.assignment2.service;

import kr.ac.jbnu.ksm.assignment2.domain.stats.dto.*;
import kr.ac.jbnu.ksm.assignment2.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService
{

    private final StatsRepository statsRepository;

    @Transactional(readOnly = true)
    public List<TopBookResponse> topBooks() {
        return statsRepository.findTopBooks(10).stream()
                .map(r -> new TopBookResponse(r.getBookId(), r.getTitle(), r.getReviewCount()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DailyReviewStatResponse> dailyReviews() {
        return statsRepository.findDailyReviews().stream()
                .map(r -> new DailyReviewStatResponse(r.getDate(), r.getReviewCount()))
                .toList();
    }
}