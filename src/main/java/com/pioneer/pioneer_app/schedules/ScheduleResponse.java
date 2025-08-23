package com.pioneer.pioneer_app.schedules;

import com.pioneer.pioneer_app.attendance.AttendanceResponse;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ScheduleResponse {
    // 일정 전달 관련 DTO
    private Long scheduleId;
    private String title;
    private LocalDate date;
    private String location;
    private String description;
    private boolean important;
    private String createdBy;

    // 중요 일정일 경우 참석자 목록 추가
    private List<AttendanceResponse> attendees;

    public ScheduleResponse(Schedule s, List<AttendanceResponse> attendees) {
        this.scheduleId = s.getScheduleId();
        this.title = s.getTitle();
        this.date = s.getDate();
        this.location = s.getLocation();
        this.description = s.getDescription();
        this.important = s.isImportant();
        this.createdBy = (s.getCreatedBy() != null) ? s.getCreatedBy().getName() : null;
        this.attendees = attendees;
    }
}
