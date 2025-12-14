package kr.ac.jbnu.ksm.assignment2.service;

import kr.ac.jbnu.ksm.assignment2.common.dto.PageResponse;
import kr.ac.jbnu.ksm.assignment2.domain.comment.dto.*;
import kr.ac.jbnu.ksm.assignment2.domain.comment.entity.Comment;
import kr.ac.jbnu.ksm.assignment2.repository.CommentLikeRepository;
import kr.ac.jbnu.ksm.assignment2.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService
{

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponse create(CommentCreateRequest req)
    {
        Comment c = Comment.builder()
                .reviewId(req.reviewId())
                .userId(req.userId())
                .content(req.content())
                .likeCount(0)
                .build();
        return CommentResponse.from(commentRepository.save(c));
    }

    @Transactional(readOnly = true)
    public PageResponse<CommentResponse> listByReview(Integer reviewId, int page, int size)
    {
        Page<CommentResponse> mapped =
                commentRepository
                        .findByReviewIdAndDeletedAtIsNull(reviewId, PageRequest.of(page, size))
                        .map(CommentResponse::from);
        return PageResponse.of(mapped);
    }

    @Transactional
    public CommentResponse update(Integer id, CommentUpdateRequest req)
    {
        Comment c = commentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow();
        c.update(req.content());
        return CommentResponse.from(c);
    }

    @Transactional
    public void delete(Integer id)
    {
        Comment c = commentRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow();
        c.softDelete();
    }

    @Transactional
    public void like(Integer id, Integer userId)
    {
        Comment c = commentRepository.findByIdAndDeletedAtIsNull(id).orElseThrow();
        if (commentLikeRepository.exists(userId, id)) return;
        commentLikeRepository.insert(userId, id);
        c.increaseLike();
    }

    @Transactional
    public void unlike(Integer id, Integer userId)
    {
        Comment c = commentRepository.findByIdAndDeletedAtIsNull(id).orElseThrow();
        if (!commentLikeRepository.exists(userId, id)) return;
        commentLikeRepository.delete(userId, id);
        c.decreaseLike();
    }
}