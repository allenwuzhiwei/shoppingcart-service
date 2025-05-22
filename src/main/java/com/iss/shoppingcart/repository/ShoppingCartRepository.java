package com.iss.shoppingcart.repository;

import com.iss.shoppingcart.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 ShoppingCart 表的数据访问接口
 提供对 shopping_cart 表的基本 CRUD 操作
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    /*
     根据 userId 查找该用户的购物车
     @param userId 用户 ID
     @return Optional<ShoppingCart> 对象
     */
    Optional<ShoppingCart> findByUserId(Long userId);
}
