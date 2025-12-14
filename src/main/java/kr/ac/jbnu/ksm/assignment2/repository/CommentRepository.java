package kr.ac.jbnu.ksm.assignment2.repository;

import kr.ac.jbnu.ksm.assignment2.domain.comment.entity.Comment;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer>
{

    Optional<Comment> findByIdAndDeletedAtIsNull(Integer id);

    Page<Comment> findByReviewIdAndDeletedAtIsNull(Integer reviewId, Pageable pageable);
}