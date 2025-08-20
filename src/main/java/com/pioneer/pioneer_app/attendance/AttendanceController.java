package com.pioneer.pioneer_app.attendance;

import com.pioneer.pioneer_app.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {
    private final AttendanceRepository repo;
    public AttendanceController(AttendanceRepository repo){
        this.repo = repo;
    }

    /** 출석 목록 조회 */
    @GetMapping
    public ApiResponse<List<Attendance>> list() {
        var items = repo.findAll();
        return ApiResponse.success("출석 목록 조회 성공", items);
    }

    /** 불참 사유 등록/수정 */
    @PostMapping("/{id}/reason")
    public ApiResponse<Void> reason(@PathVariable Long id, @RequestBody Map<String,String> body){
        var a = repo.findById(id).orElseThrow(() -> new NoSuchElementException("출석 데이터가 없습니다."));
        a.setReason(body.get("reason"));
        repo.save(a);
        return ApiResponse.success("불참 사유 저장 성공");
    }
}
