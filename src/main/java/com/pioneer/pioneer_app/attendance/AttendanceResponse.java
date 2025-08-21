package com.pioneer.pioneer_app.attendance;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class AttendanceResponse {
    // status 값을 enum명 말고 한글 (참석.불참.미정) 이 보이도록 전달하는 DTO
    private Long attendanceId;
    private Long userId;
    private String userName;
    private LocalDate date;
    private String status;
    private String reason;

    public AttendanceResponse(Attendance attendance) {
        this.attendanceId = attendance.getAttendanceId();
        this.userId = attendance.getUser().getUserId();
        this.userName = attendance.getUser().getName();
        this.date = attendance.getDate();
        this.status = attendance.getStatus().getDisplay(); // 한글 변환 코드
        this.reason = attendance.getReason();
    }
}