package kr.ac.jbnu.ksm.assignment2.domain.comment.dto;

import kr.ac.jbnu.ksm.assignment2.domain.comment.entity.Comment;
import java.time.LocalDateTime;

public record CommentResponse(
        Integer id,
        Integer reviewId,
        Integer userId,
        String content,
        Integer likeCount,
        LocalDateTime createdAt
)
{
    public static CommentResponse from(Comment c)
    {
        return new CommentResponse(
                c.getId(),
                c.getReviewId(),
                c.getUserId(),
                c.getContent(),
                c.getLikeCount(),
                c.getCreatedAt()
        );
    }
}