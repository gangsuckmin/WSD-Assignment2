package kr.ac.jbnu.ksm.assignment2.repository;

import kr.ac.jbnu.ksm.assignment2.domain.favorite.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>
{
    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);
    void deleteByUserIdAndBookId(Integer userId, Integer bookId);
    long countByUserId(Integer userId);
    Page<Favorite> findByUserId(Integer userId, Pageable pageable);
}