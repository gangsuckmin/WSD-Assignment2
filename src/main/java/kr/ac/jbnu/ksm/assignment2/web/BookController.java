package kr.ac.jbnu.ksm.assignment2.web;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksm.assignment2.common.dto.PageResponse;
import kr.ac.jbnu.ksm.assignment2.domain.book.dto.BookCreateRequest;
import kr.ac.jbnu.ksm.assignment2.domain.book.dto.BookResponse;
import kr.ac.jbnu.ksm.assignment2.domain.book.dto.BookUpdateRequest;
import kr.ac.jbnu.ksm.assignment2.domain.review.dto.ReviewResponse;
import kr.ac.jbnu.ksm.assignment2.service.BookService;
import kr.ac.jbnu.ksm.assignment2.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ReviewService reviewService;

    @PostMapping
    public BookResponse create(@Valid @RequestBody BookCreateRequest req) {
        return bookService.create(req);
    }

    @GetMapping
    public PageResponse<BookResponse> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Integer priceMin,
            @RequestParam(required = false) Integer priceMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        return bookService.list(keyword, publisher, priceMin, priceMax, page, Math.min(size, 100), sort);
    }

    @GetMapping("/{id}")
    public BookResponse get(@PathVariable Integer id) {
        return bookService.get(id);
    }

    @PatchMapping("/{id}")
    public BookResponse update(@PathVariable Integer id, @Valid @RequestBody BookUpdateRequest req) {
        return bookService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }

    @GetMapping("/{id}/reviews")
    public PageResponse<ReviewResponse> reviews(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        return reviewService.listByBook(id, page, Math.min(size, 100), sort);
    }
}