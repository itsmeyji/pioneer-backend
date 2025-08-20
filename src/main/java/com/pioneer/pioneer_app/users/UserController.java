package com.pioneer.pioneer_app.users;

import com.pioneer.pioneer_app.common.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ApiResponse<List<User>> list(@RequestParam(required = false) String q){
        var items = repo.findAll().stream()
                .filter(u -> q == null
                        || u.getUsername().contains(q)
                        || (u.getName() != null && u.getName().contains(q)))
                .toList();

        return ApiResponse.success("회원 목록 조회 성공", items);
    }

}
