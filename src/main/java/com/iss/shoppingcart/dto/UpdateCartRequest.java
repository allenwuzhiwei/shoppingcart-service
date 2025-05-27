package com.iss.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
 更新购物车商品数量请求体 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
