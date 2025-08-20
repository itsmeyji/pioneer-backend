package com.pioneer.pioneer_app.auth;

import jakarta.validation.constraints.*;

public class SignupRequest {
    @NotBlank private String username;
    @NotBlank private String password;
    @NotBlank private String name;
    @Email @NotBlank private String email;
    @Min(1) @Max(5) private Integer grade;  // 4학년 이상 (대학원생 등)은 5로 입력

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }
}
