package com.pioneer.pioneer_app.schedules;

import com.pioneer.pioneer_app.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@ToString(exclude = "createdBy")  // User, Schedule간 순환 참조 방지
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;   // 일정 id
    private String title;
    private LocalDate date;
    private String location;
    private String description;

    @Builder.Default   // default 안쓰면 인식 못함
    private boolean important = false;     // 기본값 : 안중요

    // 작성자 추적
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by") // db 칼럼명은 created_by
    private User createdBy;
}


