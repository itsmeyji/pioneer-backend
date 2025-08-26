package com.pioneer.pioneer_app.attendance;

import com.pioneer.pioneer_app.common.ApiResponse;
import com.pioneer.pioneer_app.users.User;
import com.pioneer.pioneer_app.users.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {
    private final AttendanceRepository repo;
    private final UserRepository userRepo;

    public AttendanceController(AttendanceRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    // 전체 조회
    @GetMapping
    public ApiResponse<List<AttendanceResponse>> list() {
        var items = repo.findAll().stream()
                .map(AttendanceResponse::new)
                .toList();
        return ApiResponse.success("출석 목록 조회 성공", items);
    }

    // 특정 유저 출석 조회
    @GetMapping("/user/{userId}")
    public ApiResponse<List<AttendanceResponse>> byUser(@PathVariable Long userId) {
        var items = repo.findByUserUserId(userId).stream()
                .map(AttendanceResponse::new)
                .toList();
        return ApiResponse.success("유저 출석 조회 성공", items);
    }

    // 특정 날짜 출석 조회
    @GetMapping("/date/{date}")
    public ApiResponse<List<AttendanceResponse>> byDate(@PathVariable String date) {
        LocalDate target = LocalDate.parse(date);
        var items = repo.findByDate(target).stream()
                .map(AttendanceResponse::new)
                .toList();
        return ApiResponse.success("날짜별 출석 조회 성공", items);
    }

    // 출석 등록
    @PostMapping
    public ApiResponse<AttendanceResponse> create(@RequestBody Map<String, String> body) {
        Long userId = Long.parseLong(body.get("userId"));
        LocalDate date = LocalDate.parse(body.get("date"));
        Attendance.Status status = Attendance.Status.valueOf(body.get("status"));
        String reason = body.get("reason");

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("유저가 없습니다."));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(date);
        attendance.setStatus(status);
        attendance.setReason(reason);

        var saved = repo.save(attendance);
        return ApiResponse.success("출석 등록 성공", new AttendanceResponse(saved));
    }

    // 출석 상태 수정
    @PatchMapping("/{id}")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        var a = repo.findById(id).orElseThrow(() -> new NoSuchElementException("출석 데이터가 없습니다."));
        a.setStatus(Attendance.Status.valueOf(body.get("status"))); // "ATTEND" 등
        repo.save(a);
        return ApiResponse.success("출석 상태 수정 성공");
    }

    // 불참 사유 등록/수정
    @PostMapping("/{id}/reason")
    public ApiResponse<Void> reason(@PathVariable Long id, @RequestBody Map<String,String> body){
        var a = repo.findById(id).orElseThrow(() -> new NoSuchElementException("출석 데이터가 없습니다."));
        a.setReason(body.get("reason"));
        repo.save(a);
        return ApiResponse.success("불참 사유 저장 성공");
    }
}
