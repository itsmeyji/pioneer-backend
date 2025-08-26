package com.pioneer.pioneer_app.assignments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pioneer.pioneer_app.users.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter @Setter @NoArgsConstructor
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // 엔티티 양방향 관계 걸려있으면 json변환을 못하는 문제 생김 : 이걸 해결하는 방법
        // Submission & User 상호 참조의 무한 루프에 빠졌던 것
        // 특정 필드를 json 변환 과정에서 직렬화 대상에서 제외 : jackson이 순환 참조를 끊어줌
    private User user;

    private String link;
    private String status; // ex) "제출", "채점 완료"

    private LocalDateTime submittedAt = LocalDateTime.now();

    private Integer score; // 채점 점수
}
