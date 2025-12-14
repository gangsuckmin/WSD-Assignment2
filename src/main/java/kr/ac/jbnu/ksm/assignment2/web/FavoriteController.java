package kr.ac.jbnu.ksm.assignment2.web;

import kr.ac.jbnu.ksm.assignment2.domain.favorite.dto.FavoriteResponse;
import kr.ac.jbnu.ksm.assignment2.security.CustomUserDetails; // 너 프로젝트 실제 클래스명에 맞게 수정!
import kr.ac.jbnu.ksm.assignment2.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{bookId}")
    public void add(@AuthenticationPrincipal CustomUserDetails me,
                    @PathVariable Integer bookId) {
        favoriteService.add(me.getId(), bookId);
    }

    @DeleteMapping("/{bookId}")
    public void remove(@AuthenticationPrincipal CustomUserDetails me,
                       @PathVariable Integer bookId) {
        favoriteService.remove(me.getId(), bookId);
    }

    @GetMapping
    public Page<FavoriteResponse> list(@AuthenticationPrincipal CustomUserDetails me,
                                       Pageable pageable) {
        return favoriteService.list(me.getId(), pageable);
    }

    @GetMapping("/count")
    public long count(@AuthenticationPrincipal CustomUserDetails me) {
        return favoriteService.count(me.getId());
    }
}