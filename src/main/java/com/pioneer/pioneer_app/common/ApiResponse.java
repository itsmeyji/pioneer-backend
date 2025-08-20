package com.pioneer.pioneer_app.common;

public class ApiResponse<T> {
    // 공통 응답 DTO : 모든 응답 양식을 status, message로 전달하게 함 (이전에 success : t/f)
    private final boolean status;  // true/false
    private final String message;  // 설명/에러 메시지
    private final T data;          // 성공 시 데이터(없으면 null)

    public ApiResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }

    // 헬퍼
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
