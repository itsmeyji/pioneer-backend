package com.pioneer.pioneer_app.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 예외 발생시 변경 컨트롤러, 전역 예외를 모두 처리헤 통일된 포멧으로 반환

    // 컨트롤러에서 던진 ResponseStatusException을 포맷에 맞춰 반환
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleRse(ResponseStatusException e) {
        HttpStatus code = (HttpStatus) e.getStatusCode();
        return ResponseEntity.status(code).body(ApiResponse.fail(e.getReason() == null ? code.getReasonPhrase() : e.getReason()));
    }

    // 그 외 모든 오류도 동일 포맷팅
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleEtc(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("서버 오류가 발생했습니다."));
    }
}