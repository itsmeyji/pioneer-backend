package com.pioneer.pioneer_app.users;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<User> list(@RequestParam(required = false) String q){
        return repo.findAll().stream()
                .filter(u -> q==null || u.getUsername().contains(q) ||
                        (u.getName()!=null && u.getName().contains(q)))
                .toList();
    }
}
