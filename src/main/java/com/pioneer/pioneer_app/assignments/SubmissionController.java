package com.pioneer.pioneer_app.assignments;

import com.pioneer.pioneer_app.common.ApiResponse;
import com.pioneer.pioneer_app.users.User;
import com.pioneer.pioneer_app.users.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class SubmissionController {
    private final SubmissionRepository repo;
    private final AssignmentRepository assignmentRepo;
    private final UserRepository userRepo;

    public SubmissionController(SubmissionRepository repo,
                                AssignmentRepository assignmentRepo,
                                UserRepository userRepo) {
        this.repo = repo;
        this.assignmentRepo = assignmentRepo;
        this.userRepo = userRepo;
    }

    // 제출 목록 (관리자)
    @GetMapping("/assignments/{assignmentId}/submissions")
    public ApiResponse<List<Submission>> submissionsByAssignment(@PathVariable Long assignmentId) {
        var items = repo.findByAssignmentAssignmentId(assignmentId);
        return ApiResponse.success("제출 목록 조회 성공", items);
    }

    // 내 제출 조회
    @GetMapping("/submissions/mine")
    public ApiResponse<Submission> mySubmission(@RequestParam Long assignmentId,
                                                @RequestParam Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저 없음"));
        var items = repo.findByUserAndAssignmentAssignmentId(user, assignmentId);
        if (items.isEmpty()) throw new NoSuchElementException("제출 내역 없음");
        return ApiResponse.success("내 제출 조회 성공", items.get(0));
    }

    // 과제 제출
    @PostMapping("/submissions")
    public ApiResponse<Map<String,Object>> submit(@RequestBody Map<String,String> body) {
        Long assignmentId = Long.parseLong(body.get("assignmentId"));
        Long userId = Long.parseLong(body.get("userId"));
        String link = body.get("link");

        Assignment assignment = assignmentRepo.findById(assignmentId)
                .orElseThrow(() -> new NoSuchElementException("과제가 없음"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저 없음"));

        Submission s = new Submission();
        s.setAssignment(assignment);
        s.setUser(user);
        s.setLink(link);
        s.setStatus("제출");

        Submission saved = repo.save(s);
        return ApiResponse.success("과제 제출 완료",
                Map.of("submissionId", saved.getSubmissionId(), "status", saved.getStatus()));
    }

    // 제출 과제 채점 (관리자)
    @PostMapping("/submissions/{id}/grade")
    public ApiResponse<Void> grade(@PathVariable Long id, @RequestBody Map<String,Integer> body) {
        var s = repo.findById(id).orElseThrow(() -> new NoSuchElementException("제출 없음"));
        s.setScore(body.get("score"));
        s.setStatus("채점 완료");
        repo.save(s);
        return ApiResponse.success("제출 과제 채점 완료");
    }
}
