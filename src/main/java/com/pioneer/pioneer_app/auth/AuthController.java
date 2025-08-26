package com.pioneer.pioneer_app.auth;

import com.pioneer.pioneer_app.users.User;
import com.pioneer.pioneer_app.users.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.pioneer.pioneer_app.common.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // 로그인/회원가입 (인증) 관련 컨트롤러

    private final UserRepository users;
    public AuthController(UserRepository users) {
        this.users = users;
    }

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<Map<String, Object>> signup(@Valid @RequestBody SignupRequest body) {
        // username 중복 체크
        users.findByUsername(body.getUsername()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        });

        // 저장 (지금은 평문 비밀번호로 저장, 추후 암호화 예정)
        User u = new User();
        u.setUsername(body.getUsername());
        u.setPassword(body.getPassword());
        u.setName(body.getName());
        u.setEmail(body.getEmail());
        u.setStudentNumber(body.getStudentNumber());
        u.setDepartment(body.getDepartment());
        u.setGrade(body.getGrade());
        u.setRole("user");  // 기본 권한
        u.setPosition(body.getPosition());

        users.save(u);
        return ApiResponse.success("회원가입 성공", Map.of("userId", u.getUserId()));
    }


    // 로그인 : 세션에 정보 최소로 저장
    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login (@Valid @RequestBody LoginRequest body, HttpSession session){
        User u = users.findByUsername(body.getUsername())
                .filter(x -> x.getPassword().equals(body.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));

        // 세션 저장 (프론트는 fetch에 credentials: "include" 설정 -> 로그인 유지)
        session.setAttribute("LOGIN_USER_ID", u.getUserId());
        session.setAttribute("LOGIN_USER_ROLE", u.getRole());
        session.setAttribute("LOGIN_USER_NAME", u.getName());

        return ApiResponse.success("로그인 성공", Map.of(
                "role", u.getRole(),
                "userId", u.getUserId(),
                "name", u.getName(),
                "position", u.getPosition().getDisplayName(),
                "grade", u.getGrade()
        ));
    }


    // 로그아웃: 세션 무효화
    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.success("로그아웃 성공");
    }

    // 로그인한 회원 전체 정보 조회
    @GetMapping("/me")
    public ApiResponse<User> getMyInfo(HttpSession session) {
        Object userId = session.getAttribute("LOGIN_USER_ID");  // userId 가져오기

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 상태가 아닙니다.");
        }

        User user = users.findById((Long) userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."));

        return ApiResponse.success("로그인한 회원 정보 조회 성공", user);
    }
}