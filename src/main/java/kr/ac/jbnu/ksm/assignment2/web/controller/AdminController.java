package kr.ac.jbnu.ksm.assignment2.web.controller;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController
{

    @GetMapping("/users")
    public Map<String, Object> listUsers()
    {
        // TODO: UserRepository 붙이면 실제 유저 목록으로 교체
        return Map.of(
                "timestamp", Instant.now().toString(),
                "content", List.of(
                        Map.of("id", 1, "email", "user1@example.com", "role", "ROLE_USER", "active", true),
                        Map.of("id", 2, "email", "admin@example.com", "role", "ROLE_ADMIN", "active", true)
                ),
                "totalElements", 2
        );
    }

    @PatchMapping("/users/{id}/deactivate")
    public Map<String, Object> deactivateUser(@PathVariable Integer id)
    {
        // TODO: 실제로는 User 엔티티의 active=false 업데이트
        return Map.of(
                "timestamp", Instant.now().toString(),
                "message", "user deactivated (mock)",
                "userId", id
        );
    }

    @GetMapping("/stats")
    public Map<String, Object> stats()
    {
        // TODO: 실제로는 repository count() 등으로 교체
        return Map.of(
                "timestamp", Instant.now().toString(),
                "users", 2,
                "posts", 0,
                "comments", 0
        );
    }
}