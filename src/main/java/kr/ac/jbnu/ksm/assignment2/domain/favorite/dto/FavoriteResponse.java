package kr.ac.jbnu.ksm.assignment2.domain.favorite.dto;

import java.time.LocalDateTime;

public record FavoriteResponse(
        Integer id,
        Integer bookId,
        LocalDateTime updatedAt
) {
    public static FavoriteResponse from(Integer id, Integer bookId, LocalDateTime updatedAt)
    {
        return new FavoriteResponse(id, bookId, updatedAt);
    }
}