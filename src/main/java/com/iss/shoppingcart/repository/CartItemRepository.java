package com.iss.shoppingcart.repository;

import com.iss.shoppingcart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 CartItem 表的数据访问接口
 提供对 cart_item 表的基本 CRUD 操作
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /*
     查询某个用户购物车中的所有商品项
     @param userId 用户 ID
     @return 购物车商品列表
     */
    List<CartItem> findByUserId(Long userId);

    /*
     查询用户购物车中是否已存在某商品（用于数量合并）
     @param userId 用户 ID
     @param productId 商品 ID
     @return Optional<CartItem>
     */
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    /*
     删除某个用户购物车中的某商品
     @param userId 用户 ID
     @param productId 商品 ID
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
