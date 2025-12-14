package kr.ac.jbnu.ksm.assignment2.web;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksm.assignment2.common.dto.PageResponse;
import kr.ac.jbnu.ksm.assignment2.domain.review.dto.*;
import kr.ac.jbnu.ksm.assignment2.domain.comment.dto.CommentCreateRequest;
import kr.ac.jbnu.ksm.assignment2.domain.comment.dto.CommentResponse;
import kr.ac.jbnu.ksm.assignment2.service.ReviewService;
import kr.ac.jbnu.ksm.assignment2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController
{

    private final ReviewService reviewService;
    private final CommentService commentService;

    @PostMapping
    public ReviewResponse create(@Valid @RequestBody ReviewCreateRequest req)
    {
        return reviewService.create(req);
    }

    @GetMapping
    public PageResponse<ReviewResponse> list(
            @RequestParam(required = false) Integer bookId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    )
    {
        return reviewService.list(bookId, userId, page, Math.min(size, 100), sort);
    }

    @GetMapping("/{id}")
    public ReviewResponse get(@PathVariable Integer id)
    {
        return reviewService.get(id);
    }

    @PatchMapping("/{id}")
    public ReviewResponse update(@PathVariable Integer id, @Valid @RequestBody ReviewUpdateRequest req)
    {
        return reviewService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id)
    {
        reviewService.delete(id);
    }

    @PostMapping("/{reviewId}/comments")
    public CommentResponse createComment(
            @PathVariable Integer reviewId,
            @RequestBody CommentCreateRequest req
    ) {
        return commentService.create(
                new CommentCreateRequest(reviewId, req.userId(), req.content())
        );
    }

    @GetMapping("/{reviewId}/comments")
    public PageResponse<CommentResponse> comments(
            @PathVariable Integer reviewId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return commentService.listByReview(reviewId, page, size);
    }
}