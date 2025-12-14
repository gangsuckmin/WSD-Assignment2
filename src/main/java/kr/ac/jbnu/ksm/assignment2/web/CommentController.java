package kr.ac.jbnu.ksm.assignment2.web;

import kr.ac.jbnu.ksm.assignment2.domain.comment.dto.CommentResponse;
import kr.ac.jbnu.ksm.assignment2.domain.comment.dto.CommentUpdateRequest;
import kr.ac.jbnu.ksm.assignment2.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PatchMapping("/{id}")
    public CommentResponse update(
            @PathVariable Integer id,
            @RequestBody CommentUpdateRequest req
    ) {
        return commentService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        commentService.delete(id);
    }

    @PostMapping("/{id}/like")
    public void like(
            @PathVariable Integer id,
            @RequestParam Integer userId
    ) {
        commentService.like(id, userId);
    }

    @DeleteMapping("/{id}/like")
    public void unlike(
            @PathVariable Integer id,
            @RequestParam Integer userId
    ) {
        commentService.unlike(id, userId);
    }
}