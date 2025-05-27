package com.iss.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private BigDecimal price;    // （前端传入）临时传入商品价格，后续将由后端自动填充
}
