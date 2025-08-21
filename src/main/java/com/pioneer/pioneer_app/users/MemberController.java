package com.pioneer.pioneer_app.users;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    // "/api/members" 구현 코드
    // "/users"에서는 관리자 권한으로만 조회 가능, 이건 사용자 목록 출력용 코드

    private final UserRepository repo;

    public MemberController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Map<String,Object> list(@RequestParam(required = false) String q) {
        List<MemberResponse> items = repo.findAll().stream()
                .filter(u -> q == null
                        || u.getName().contains(q)
                        || (u.getDepartment()!=null && u.getDepartment().contains(q))
                        || (u.getStudentNumber()!=null && u.getStudentNumber().contains(q)))
                .map(MemberResponse::new)
                .toList();

        return Map.of(
                "status", "success",
                "message", "연구실 멤버 목록 조회 성공",
                "items", items,
                "total", items.size()
        );
    }
}
