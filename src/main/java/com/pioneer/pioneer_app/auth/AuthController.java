package com.pioneer.pioneer_app.auth;

import com.pioneer.pioneer_app.users.User;
import com.pioneer.pioneer_app.users.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Map<String, Object> signup(@Valid @RequestBody SignupRequest body) {
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
        u.setGrade(body.getGrade());
        u.setRole("user"); // 기본 권한

        users.save(u);
        return Map.of("success", true, "userId", u.getUserId());
    }

    // 로그인 : 세션에 정보 최소로 저장
    @PostMapping("/login")
    public Map<String, Object> login (@Valid @RequestBody LoginRequest body, HttpSession session){
        User u = users.findByUsername(body.getUsername())
                .filter(x -> x.getPassword().equals(body.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));

        // 세션 저장 (프론트는 fetch에 credentials: "include" 설정 -> 로그인유지용인듯)
        session.setAttribute("LOGIN_USER_ID", u.getUserId());
        session.setAttribute("LOGIN_USER_ROLE", u.getRole());
        session.setAttribute("LOGIN_USER_NAME", u.getName());

        return Map.of(
                "success", true,
                "role", u.getRole(),
                "userId", u.getUserId(),
                "name", u.getName()
        );
    }

    // 로그아웃: 세션 무효화
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        return Map.of("success", true);
    }
}