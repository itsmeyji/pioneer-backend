package com.pioneer.pioneer_app.users;

import lombok.Getter;

@Getter
public class UserResponse {
    // 변수명 & 화면 출력 코드가 다른 것을 반영,
    // 화면 출력 세팅을 위한 DTO 추가

    private Long userId;
    private String username;
    private String name;
    private String email;
    private Integer grade;
    private String role;
    private String position; // 한글로 표시됨


    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.grade = user.getGrade();
        this.role = user.getRole();
        this.position = user.getPosition().getDisplayName();
    }
}
