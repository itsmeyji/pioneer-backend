package com.pioneer.pioneer_app.schedules;

import com.pioneer.pioneer_app.attendance.AttendanceRepository;
import com.pioneer.pioneer_app.attendance.AttendanceResponse;
import com.pioneer.pioneer_app.users.User;
import com.pioneer.pioneer_app.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository schedules;
    private final UserRepository users;
    private final AttendanceRepository attendances;

    public ScheduleService(ScheduleRepository schedules, UserRepository users, AttendanceRepository attendances) {
            this.schedules = schedules;
            this.users = users;
            this.attendances = attendances;
    }

    // 일정 목록 조회
    public List<ScheduleResponse> findAll() {
        return schedules.findAll().stream()
                .map(s -> {
                    List<AttendanceResponse> attendees = s.isImportant()
                            ? attendances.findByDate(s.getDate()).stream()
                            .map(AttendanceResponse::new)
                            .toList()
                            : null;
                    return new ScheduleResponse(s, attendees);
                })
                .toList();
    }

    // 일정 등록
    public Schedule create(Schedule s, Long userId) {
        User admin = users.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자(관리자)를 찾을 수 없습니다."));

        s.setCreatedBy(admin);
        return schedules.save(s);
    }

    // 일정 수정
    public Schedule update(Long id, Schedule req) {
        Schedule s = schedules.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        if (req.getTitle() != null) s.setTitle(req.getTitle());
        if (req.getDate() != null) s.setDate(req.getDate());
        if (req.getLocation() != null) s.setLocation(req.getLocation());
        if (req.getDescription() != null) s.setDescription(req.getDescription());
        s.setImportant(req.isImportant());

        return schedules.save(s);

    }

    // 일정 삭제
    public void delete(Long id) {
        if (!schedules.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }
        schedules.deleteById(id);
    }

    // 날짜별 조회
    public List<ScheduleResponse> findByDate(LocalDate date) {
        return schedules.findByDate(date).stream()
                .map(s -> {
                    List<AttendanceResponse> attendees = s.isImportant()
                            ? attendances.findByDate(s.getDate()).stream()
                            .map(AttendanceResponse::new)
                            .toList()
                            : null;
                    return new ScheduleResponse(s, attendees);
                })
                .toList();
    }

}
