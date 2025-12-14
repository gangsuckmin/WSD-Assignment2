package kr.ac.jbnu.ksm.assignment2.service;

import kr.ac.jbnu.ksm.assignment2.common.dto.PageResponse;
import kr.ac.jbnu.ksm.assignment2.domain.book.dto.BookCreateRequest;
import kr.ac.jbnu.ksm.assignment2.domain.book.dto.BookResponse;
import kr.ac.jbnu.ksm.assignment2.domain.book.dto.BookUpdateRequest;
import kr.ac.jbnu.ksm.assignment2.domain.book.entity.Book;
import kr.ac.jbnu.ksm.assignment2.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookService
{

    private final BookRepository bookRepository;

    @Transactional
    public BookResponse create(BookCreateRequest req)
    {
        if (bookRepository.existsByIsbn13(req.isbn13()))
        {
            throw new IllegalArgumentException("DUPLICATE_RESOURCE: isbn13 already exists");
        }

        Book book = Book.builder()
                .isbn13(req.isbn13())
                .title(req.title())
                .publisher(req.publisher())
                .publishedDate(req.publishedDate())
                .price(req.price())
                .currency(req.currency())
                .stock(req.stock())
                .likeCount(0)
                .reviewCount(0)
                .salesCount(0)
                .avgRating(new BigDecimal("0.00"))
                .build();

        return BookResponse.from(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public PageResponse<BookResponse> list(String keyword, String publisher, Integer priceMin, Integer priceMax,
                                           int page, int size, String sort)
    {

        Sort s = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, s);

        Page<BookResponse> mapped = bookRepository.search(
                emptyToNull(keyword),
                emptyToNull(publisher),
                priceMin,
                priceMax,
                pageable
        ).map(BookResponse::from);

        return PageResponse.of(mapped);
    }

    @Transactional(readOnly = true)
    public BookResponse get(Integer id)
    {
        Book b = bookRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: book not found"));
        return BookResponse.from(b);
    }

    @Transactional
    public BookResponse update(Integer id, BookUpdateRequest req)
    {
        Book b = bookRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: book not found"));

        b.update(req.title(), req.publisher(), req.publishedDate(), req.price(), req.currency(), req.stock());
        return BookResponse.from(b);
    }

    @Transactional
    public void delete(Integer id)
    {
        Book b = bookRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("RESOURCE_NOT_FOUND: book not found"));
        b.softDelete();
    }

    private static String emptyToNull(String s)
    {
        return (s == null || s.isBlank()) ? null : s;
    }

    private static Sort parseSort(String sort)
    {
        // sort=createdAt,DESC 기본
        if (sort == null || sort.isBlank())
            return Sort.by(Sort.Direction.DESC, "createdAt");
        String[] parts = sort.split(",");
        if (parts.length != 2)
            return Sort.by(Sort.Direction.DESC, "createdAt");
        Sort.Direction dir = "ASC".equalsIgnoreCase(parts[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(dir, parts[0]);
    }
}