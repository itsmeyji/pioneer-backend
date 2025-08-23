package com.pioneer.pioneer_app.schedules;

import com.pioneer.pioneer_app.common.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    // 일정 목록 조회 -> 중요 일정이면 참석자 포함
    @GetMapping
    public ApiResponse<List<ScheduleResponse>> list() {
        return ApiResponse.success("일정 목록 조회 성공", service.findAll());
    }

    // 일정 등록 (관리자만)
    @PostMapping
    public ApiResponse<Map<String, Object>> create(@RequestBody Schedule req, HttpSession session) {
        Object role = session.getAttribute("LOGIN_USER_ROLE");
        Long userId = (Long) session.getAttribute("LOGIN_USER_ID");

        if (role == null || !"admin".equals(role.toString())) {
            throw new RuntimeException("관리자만 등록 가능합니다.");
        }

        Schedule s = service.create(req, userId);
        return ApiResponse.success("일정 등록 성공", Map.of("scheduleId", s.getScheduleId()));
    }

    // 일정 수정
    @PatchMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody Schedule req) {
        service.update(id, req);
        return ApiResponse.success("일정 수정 성공");
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.success("일정 삭제 성공");
    }

    // 날짜별 일정 조회
    @GetMapping("/date/{date}")
    public ApiResponse<List<ScheduleResponse>> byDate(@PathVariable String date) {
        LocalDate target = LocalDate.parse(date);
        List<ScheduleResponse> result = service.findByDate(target);
        return ApiResponse.success("날짜별 일정 조회 성공", result);
    }

}
