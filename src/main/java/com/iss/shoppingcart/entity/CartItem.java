package com.iss.shoppingcart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 CartItem 实体类, 对应数据库表 cart_item
 用于表示购物车中的每一件商品
 */
@Data
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 主键，自增

    @Column(name = "user_id", nullable = false)
    private Long userId;  // 用户 ID（用于查询该用户的所有购物车项）

    @Column(name = "product_id", nullable = false)
    private Long productId;  // 商品 ID

    @Column(name = "quantity", nullable = false)
    private Integer quantity;  // 购买数量

    @Column(name = "price_at_add", precision = 10, scale = 2)
    private BigDecimal priceAtAdd;  // 添加购物车时商品价格，用于后续对比变价

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;
}
