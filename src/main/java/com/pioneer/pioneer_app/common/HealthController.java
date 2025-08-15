package com.pioneer.pioneer_app.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    // 서버 살아있는지 확인하는 핑
    // 실행 후 "localhost:8080/api/ping" 실행시 pong 나오면 성공
    @GetMapping("/api/ping")
    public String ping() {
        return "pong";
    }

    // 비슷한건데 아예 페이지로 보여주는 것
    // "localhost:8080/health" 실행시 OK 나오면 성공
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}