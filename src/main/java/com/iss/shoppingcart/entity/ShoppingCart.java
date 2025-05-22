package com.iss.shoppingcart.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/*
 ShoppingCart 实体类, 对应数据库表 shopping_cart
 用于表示用户的购物车（按 user_id 一人一个）
 */
@Data
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 主键，自增

    @Column(name = "user_id", nullable = false)
    private Long userId;  // 用户 ID，标识该购物车属于哪个用户

    @Column(name = "create_user")
    private String createUser;  // 创建人（操作员名或系统）

    @Column(name = "update_user")
    private String updateUser;  // 更新人

    @Column(name = "create_datetime")
    private LocalDateTime createDatetime;  // 创建时间

    @Column(name = "update_datetime")
    private LocalDateTime updateDatetime;  // 更新时间
}
