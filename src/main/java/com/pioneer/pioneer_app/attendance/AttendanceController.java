package com.pioneer.pioneer_app.attendance;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {
    private final AttendanceRepository repo;
    public AttendanceController(AttendanceRepository repo){
        this.repo = repo;
    }

    @GetMapping
    public List<Attendance> list(){ return repo.findAll(); }

    @PostMapping("/{id}/reason")
    public Map<String,Object> reason(@PathVariable Long id, @RequestBody Map<String,String> body){
        var a = repo.findById(id).orElseThrow();
        a.setReason(body.get("reason"));
        repo.save(a);
        return Map.of("success", true);
    }
}
