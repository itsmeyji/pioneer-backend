package com.pioneer.pioneer_app.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    // 로그인 : id pw 받아서 매핑
    // validation -> @NotBlank로 공백입력 방지

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
