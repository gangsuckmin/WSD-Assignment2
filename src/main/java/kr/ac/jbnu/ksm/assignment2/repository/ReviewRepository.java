package kr.ac.jbnu.ksm.assignment2.repository;

import kr.ac.jbnu.ksm.assignment2.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findByIdAndDeletedAtIsNull(Integer id);

    @Query("""
        select r from Review r
        where r.deletedAt is null
          and (:bookId is null or r.bookId = :bookId)
          and (:userId is null or r.userId = :userId)
        """)
    Page<Review> search(Integer bookId, Integer userId, Pageable pageable);

    @Query("""
        select r from Review r
        where r.deletedAt is null
          and r.bookId = :bookId
        """)
    Page<Review> findByBook(Integer bookId, Pageable pageable);
}