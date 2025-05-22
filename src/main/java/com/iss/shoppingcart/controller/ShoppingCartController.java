package com.iss.shoppingcart.controller;

import com.iss.shoppingcart.config.ApiResponse;
import com.iss.shoppingcart.dto.AddCartRequest;
import com.iss.shoppingcart.dto.UpdateCartRequest;
import com.iss.shoppingcart.entity.CartItem;
import com.iss.shoppingcart.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 ShoppingCartController 控制器类, 提供购物车操作相关的 RESTful API 接口
 */
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /*
     获取用户的购物车主表信息（shopping_cart）
     */
    @GetMapping("/info")
    public ApiResponse<Object> getShoppingCartInfo(@RequestParam Long userId) {
        return shoppingCartService.getShoppingCartByUserId(userId)
                .map(cart -> ApiResponse.success((Object) cart))
                .orElseGet(() -> ApiResponse.fail("该用户购物车不存在"));
    }

    /*
     获取当前用户购物车中的所有商品项
     */
    @GetMapping("/items")
    public ApiResponse<List<CartItem>> getCartItems(@RequestParam Long userId) {
        List<CartItem> items = shoppingCartService.getCartItemsByUserId(userId);
        return ApiResponse.success(items);
    }

    /*
     添加商品到购物车（若已存在则叠加数量）
     */
    @PostMapping("/add")
    public ApiResponse<CartItem> addToCart(@RequestBody AddCartRequest request) {
        CartItem added = shoppingCartService.addProductToCart(
                request.getUserId(), request.getProductId(), request.getQuantity());
        return ApiResponse.success(added);
    }

    /*
     修改购物车中商品的数量
     */
    @PutMapping("/update")
    public ApiResponse<CartItem> updateQuantity(@RequestBody UpdateCartRequest request) {
        CartItem updated = shoppingCartService.updateProductQuantity(
                request.getUserId(), request.getProductId(), request.getQuantity());
        return ApiResponse.success(updated);
    }

    /*
     从购物车中移除某个商品
     */
    @DeleteMapping("/remove")
    public ApiResponse<String> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        shoppingCartService.removeProductFromCart(userId, productId);
        return ApiResponse.success("商品已从购物车移除");
    }

    /*
     清空用户购物车
     */
    @DeleteMapping("/clear")
    public ApiResponse<String> clearCart(@RequestParam Long userId) {
        shoppingCartService.clearCart(userId);
        return ApiResponse.success("购物车已清空");
    }
}
