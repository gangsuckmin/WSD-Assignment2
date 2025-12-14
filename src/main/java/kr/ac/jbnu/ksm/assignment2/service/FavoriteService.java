package kr.ac.jbnu.ksm.assignment2.service;

import kr.ac.jbnu.ksm.assignment2.domain.favorite.dto.FavoriteResponse;
import kr.ac.jbnu.ksm.assignment2.domain.favorite.entity.Favorite;
import kr.ac.jbnu.ksm.assignment2.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService
{

    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void add(Integer userId, Integer bookId)
    {
        if (favoriteRepository.existsByUserIdAndBookId(userId, bookId)) return; // 중복이면 무시(또는 409로 바꿔도 됨)
        favoriteRepository.save(Favorite.builder()
                .userId(userId)
                .bookId(bookId)
                .build());
    }

    @Transactional
    public void remove(Integer userId, Integer bookId)
    {
        favoriteRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    @Transactional(readOnly = true)
    public Page<FavoriteResponse> list(Integer userId, Pageable pageable)
    {
        return favoriteRepository.findByUserId(userId, pageable)
                .map(f -> FavoriteResponse.from(f.getId(), f.getBookId(), f.getUpdatedAt()));
    }

    @Transactional(readOnly = true)
    public long count(Integer userId)
    {
        return favoriteRepository.countByUserId(userId);
    }
}