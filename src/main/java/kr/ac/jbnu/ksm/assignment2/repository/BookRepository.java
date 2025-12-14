package kr.ac.jbnu.ksm.assignment2.repository;

import kr.ac.jbnu.ksm.assignment2.domain.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer>
{

    Optional<Book> findByIdAndDeletedAtIsNull(Integer id);

    boolean existsByIsbn13(String isbn13);

    @Query("""
        select b from Book b
        where b.deletedAt is null
          and (:keyword is null or b.title like concat('%', :keyword, '%'))
          and (:publisher is null or b.publisher = :publisher)
          and (:priceMin is null or b.price >= :priceMin)
          and (:priceMax is null or b.price <= :priceMax)
        """)
    Page<Book> search(
            String keyword,
            String publisher,
            Integer priceMin,
            Integer priceMax,
            Pageable pageable
    );
}