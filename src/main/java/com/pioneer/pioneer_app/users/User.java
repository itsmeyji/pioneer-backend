package com.pioneer.pioneer_app.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")  // 예약어 user 대신에 db 만들 때는 users, 코드에서는 user 사용
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;
    private String name;             //이름
    private String email;            //이메일
    private Integer grade;           //학년
    private String department;       //학과
    private String studentNumber;    // 학번

    // 직책 (영문 저장, 한글 변환은 getDisplayName() 활용)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    private String role;

    public enum Position {
        UNDERGRADUATE("학부"),
        MASTER("석박사"),
        OTHER("기타");

        private final String displayName;

        Position(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}