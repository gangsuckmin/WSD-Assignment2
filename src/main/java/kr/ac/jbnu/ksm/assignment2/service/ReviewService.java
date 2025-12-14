package kr.ac.jbnu.ksm.assignment2.service;

import kr.ac.jbnu.ksm.assignment2.common.dto.PageResponse;
import kr.ac.jbnu.ksm.assignment2.domain.review.dto.*;
import kr.ac.jbnu.ksm.assignment2.domain.review.entity.Review;
import kr.ac.jbnu.ksm.assignment2.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.ac.jbnu.ksm.assignment2.repository.ReviewLikeRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional
    public ReviewResponse create(ReviewCreateRequest req)
    {
        Review r = Review.builder()
                .bookId(req.bookId())
                .userId(req.userId())
                .rating(req.rating())
                .content(req.content())
                .likeCount(0)
                .commentCount(0)
                .build();
        return ReviewResponse.from(reviewRepository.save(r));
    }

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> list(Integer bookId, Integer userId, int page, int size, String sort)
    {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<ReviewResponse> mapped = reviewRepository.search(bookId, userId, pageable).map(ReviewResponse::from);
        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> listByBook(Integer bookId, int page, int size, String sort)
    {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<ReviewResponse> mapped = reviewRepository.findByBook(bookId, pageable).map(ReviewResponse::from);
        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public ReviewResponse get(Integer id)
    {
        Review r = reviewRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: review not found"));
        return ReviewResponse.from(r);
    }

    @Transactional
    public ReviewResponse update(Integer id, ReviewUpdateRequest req)
    {
        Review r = reviewRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: review not found"));
        r.update(req.rating(), req.content());
        return ReviewResponse.from(r);
    }

    @Transactional
    public void delete(Integer id)
    {
        Review r = reviewRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: review not found"));
        r.softDelete();
    }

    private static Sort parseSort(String sort)
    {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "createdAt");
        String[] parts = sort.split(",");
        if (parts.length != 2) return Sort.by(Sort.Direction.DESC, "createdAt");
        Sort.Direction dir = "ASC".equalsIgnoreCase(parts[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(dir, parts[0]);
    }

    @Transactional
    public void like(Integer reviewId, Integer userId) {
        Review r = reviewRepository.findByIdAndDeletedAtIsNull(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: review not found"));

        if (reviewLikeRepository.existsByUserIdAndReviewId(userId, reviewId)) return;

        reviewLikeRepository.insert(userId, reviewId);
        r.increaseLikeCount();
    }

    @Transactional
    public void unlike(Integer reviewId, Integer userId) {
        Review r = reviewRepository.findByIdAndDeletedAtIsNull(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: review not found"));

        if (!reviewLikeRepository.existsByUserIdAndReviewId(userId, reviewId)) return;

        reviewLikeRepository.deleteByUserIdAndReviewId(userId, reviewId);
        r.decreaseLikeCount();
    }
}