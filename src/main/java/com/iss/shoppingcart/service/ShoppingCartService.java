package com.iss.shoppingcart.service;

import com.iss.shoppingcart.entity.CartItem;
import com.iss.shoppingcart.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/*
 ShoppingCartService 接口, 负责购物车相关的业务逻辑处理
 */
public interface ShoppingCartService {

    /*
     查询用户的购物车（主表）通过user ID来实现
     */
    Optional<ShoppingCart> getShoppingCartByUserId(Long userId);

    /*
     向购物车添加商品（若已存在则叠加数量）
     */
    CartItem addProductToCart(Long userId, Long productId, Integer quantity, BigDecimal price);

    /*
     获取用户购物车中的所有商品项
     */
    List<CartItem> getCartItemsByUserId(Long userId);

    /*
     修改购物车中商品的数量
     */
    CartItem updateProductQuantity(Long userId, Long productId, Integer newQuantity, BigDecimal price);

    /*
     从购物车中删除一个商品
     */
    void removeProductFromCart(Long userId, Long productId);

    /*
     清空用户购物车
     */
    void clearCart(Long userId);
}
