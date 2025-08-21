package com.pioneer.pioneer_app.users;
import lombok.Generated;
import lombok.Getter;

@Getter
public class MemberResponse {
    // 회원 목록 조회시 사용할 (학과/학번/이름/직책)만 반환하는 DTO

    private String department;
    private String studentNumber;
    private String name;
    private String position; // 한글로 표시됨

    public MemberResponse(User user) {
        this.department = user.getDepartment();
        this.studentNumber = user.getStudentNumber();
        this.name = user.getName();
        this.position = user.getPosition().getDisplayName();
    }
}
