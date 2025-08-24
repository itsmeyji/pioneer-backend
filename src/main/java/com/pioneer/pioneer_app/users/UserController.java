package com.pioneer.pioneer_app.users;

import com.pioneer.pioneer_app.common.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // api/users, 사용자 목록 조회 api
    // q 파라미터로 해당 값 들어간 사용자만 필터링 가능

    private final UserRepository repo;
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ApiResponse<Map<String,Object>> list(@RequestParam(required = false) String q, HttpSession session){
        Object role = session.getAttribute("LOGIN_USER_ROLE");
        if (role == null || !"admin".equals(role.toString())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자만 접근 가능합니다.");
        }
        var items = repo.findAll().stream()
                .filter(u -> q == null
                        || u.getUsername().contains(q)
                        || (u.getName()!=null && u.getName().contains(q)))
                .map(UserResponse::new)
                .toList();

        return ApiResponse.success("회원 목록 조회 성공", Map.of(
                "items", items,
                "total", items.size()
        ));
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> delete(@PathVariable Long userId, HttpSession session) {
        Object role = session.getAttribute("LOGIN_USER_ROLE");
        if (role == null || !"admin".equals(role.toString())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "관리자만 접근 가능합니다.");
        }

        var user = repo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."));

        repo.delete(user);

        return ApiResponse.success("회원 정보 삭제 완료");
    }
}
