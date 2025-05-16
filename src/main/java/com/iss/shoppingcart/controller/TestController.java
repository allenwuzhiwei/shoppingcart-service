package com.iss.shoppingcart.controller;

import com.iss.shoppingcart.config.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getAllProducts() {
        return ResponseEntity.ok(new ApiResponse<>(true, "The shopping carts retrieved successfully", "test"));
    }
}
