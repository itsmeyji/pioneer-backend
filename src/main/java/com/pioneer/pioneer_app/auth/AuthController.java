package com.pioneer.pioneer_app.auth;

import com.pioneer.pioneer_app.users.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    public AuthController(UserRepository users) { this.users = users; }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,String> req) {
        String id = req.get("username");
        String pw = req.get("password");

        return users.findByUsername(id)
                .filter(u -> u.getPassword().equals(pw))
                .map(u -> Map.<String, Object>of(
                        "success", true,
                        "role", u.getRole(),
                        "userId", u.getUserId(),
                        "name", u.getName()
                ))
                .orElseGet(() -> Map.<String, Object>of("success", false));
    }
}
