package com.pioneer.pioneer_app.assignments;

import com.pioneer.pioneer_app.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentRepository repo;

    public AssignmentController(AssignmentRepository repo) {
        this.repo = repo;
    }

    // 과제 목록 조회
    @GetMapping
    public ApiResponse<List<Assignment>> list(@RequestParam(required = false) String type) {
        List<Assignment> items;
        if (type != null) {
            items = repo.findByType(Assignment.Type.valueOf(type.toUpperCase()));
        } else {
            items = repo.findAll();
        }
        return ApiResponse.success("과제 목록 조회 성공", items);
    }

    // 과제 등록 (관리자)
    @PostMapping
    public ApiResponse<Map<String,Object>> create(@RequestBody Assignment req) {
        Assignment saved = repo.save(req);
        return ApiResponse.success("과제 등록 완료",
                Map.of("assignmentId", saved.getAssignmentId()));
    }
}
