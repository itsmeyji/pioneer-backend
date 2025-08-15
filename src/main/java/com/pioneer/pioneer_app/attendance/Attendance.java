package com.pioneer.pioneer_app.attendance;

import jakarta.persistence.*; import lombok.*;
import java.time.LocalDate;

@Entity @Getter @Setter @NoArgsConstructor
public class Attendance {
    @Id @GeneratedValue private Long attendanceId;
    private Long userId;
    private LocalDate date;
    private String status; // "참석","불참","미정"
    private String reason; // 불참 사유
}
