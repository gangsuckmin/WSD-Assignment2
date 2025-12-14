package kr.ac.jbnu.ksm.assignment2.domain.review.dto;

import kr.ac.jbnu.ksm.assignment2.domain.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Integer id,
        Integer userId,
        Integer bookId,
        Integer rating,
        String content,
        Integer likeCount,
        Integer commentCount,
        LocalDateTime createdAt
)
{
    public static ReviewResponse from(Review r)
    {
        return new ReviewResponse(
                r.getId(),
                r.getUserId(),
                r.getBookId(),
                r.getRating(),
                r.getContent(),
                r.getLikeCount(),
                r.getCommentCount(),
                r.getCreatedAt()
        );
    }
}