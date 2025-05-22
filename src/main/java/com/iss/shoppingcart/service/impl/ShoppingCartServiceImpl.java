package com.iss.shoppingcart.service.impl;

import com.iss.shoppingcart.entity.CartItem;
import com.iss.shoppingcart.entity.ShoppingCart;
import com.iss.shoppingcart.repository.CartItemRepository;
import com.iss.shoppingcart.repository.ShoppingCartRepository;
import com.iss.shoppingcart.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
 ShoppingCartService 接口实现类
 实现用户购物车的业务逻辑处理，包括添加、查询、删除等
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    /*
     根据 userId 获取该用户的购物车（主表）
     */
    @Override
    public Optional<ShoppingCart> getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    /*
     根据 userId 获取用户购物车中所有商品项
     */
    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    /*
     向购物车中添加商品，如果已存在该商品，则叠加数量
     使用懒加载创建购物车：用户第一次添加商品时，如果主表中没有购物车，就自动帮他创建。
     */
    @Override
    @Transactional
    public CartItem addProductToCart(Long userId, Long productId, Integer quantity) {

        // Step 1: 查询用户购物车主表是否存在，如果不存在则自动创建
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()) {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUserId(userId);
            newCart.setCreateDatetime(LocalDateTime.now());
            newCart.setUpdateDatetime(LocalDateTime.now());
            newCart.setCreateUser("system"); // 或传参设置
            newCart.setUpdateUser("system");
            shoppingCartRepository.save(newCart);
        }

        // Step 2: 继续处理商品项逻辑
        Optional<CartItem> optionalItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        CartItem item;
        if (optionalItem.isPresent()) {
            // 若该商品已在购物车中，叠加数量
            item = optionalItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setUpdateDatetime(LocalDateTime.now());
        } else {
            // 新增商品项
            item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setCreateDatetime(LocalDateTime.now());
            item.setUpdateDatetime(LocalDateTime.now());
            item.setCreateUser("system");
            item.setUpdateUser("system");
            item.setPriceAtAdd(BigDecimal.ZERO); // 实际项目中可调用 product-service 获取价格
        }

        return cartItemRepository.save(item);
    }


    /*
     更新购物车中某个商品的数量
     */
    @Transactional
    @Override
    public CartItem updateProductQuantity(Long userId, Long productId, Integer newQuantity) {
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(newQuantity);
        item.setUpdateDatetime(LocalDateTime.now());
        return cartItemRepository.save(item);
    }

    /*
     从购物车中删除某个商品
     */
    @Transactional
    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    /*
     清空用户购物车中的所有商品项
     */
    @Transactional
    @Override
    public void clearCart(Long userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        cartItemRepository.deleteAll(items);
    }
}
