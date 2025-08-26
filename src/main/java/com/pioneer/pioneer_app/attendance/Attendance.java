package com.pioneer.pioneer_app.attendance;

import com.pioneer.pioneer_app.users.User;
import jakarta.persistence.*; import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name="attendance")
@Getter @Setter @NoArgsConstructor
public class Attendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    // User와 연결 (FK)
    @ManyToOne(fetch = FetchType.LAZY)    // 출석 연결 불가 해결 : getUser().getName()으로 역추적도 가능
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String reason;

    // 출석 상태 참석/불참/미정 중에 하나만 선택 가능하도록 enum 설정
    public enum Status {
        ATTEND("참석"),
        ABSENT("불참"),
        UNKNOWN("미정");

        private final String display;
        Status(String display) { this.display = display; }
        public String getDisplay() {return display; }
    }
}
