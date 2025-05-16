package com.iss.shoppingcart.config;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    // Default constructor
    public ApiResponse() {}

    // All-args constructor
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Static factory methods
    @SuppressWarnings("unchecked")
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "success", data);
    }

    @SuppressWarnings("unchecked")
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "success", null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}



