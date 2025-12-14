package kr.ac.jbnu.ksm.assignment2.common.dto;

import org.springframework.data.domain.Page;
import java.util.List;

public class PageResponse<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final String sort;

    public PageResponse(List<T> content, int page, int size, long totalElements, int totalPages, String sort) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.sort = sort;
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public String getSort() { return sort; }

    public static <T> PageResponse<T> of(Page<T> pageData) {
        String sort = pageData.getSort().isEmpty() ? "" : pageData.getSort().toString();
        return new PageResponse<>(
                pageData.getContent(),
                pageData.getNumber(),
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages(),
                sort
        );
    }
}
