package com.iss.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 添加购物车请求体 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
