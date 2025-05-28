package com.iss.shoppingcart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iss.shoppingcart.dto.AddCartRequest;
import com.iss.shoppingcart.dto.UpdateCartRequest;
import com.iss.shoppingcart.entity.CartItem;
import com.iss.shoppingcart.entity.ShoppingCart;
import com.iss.shoppingcart.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShoppingCartController.class)
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(shoppingCartService);
    }

    @Test
    void testGetShoppingCartInfo_Found() throws Exception {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(1L);
        when(shoppingCartService.getShoppingCartByUserId(1L))
                .thenReturn(Optional.of(cart));

        mockMvc.perform(get("/cart/info")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testGetCartItems_Success() throws Exception {
        CartItem item = new CartItem();
        item.setUserId(1L);
        item.setProductId(101L);
        when(shoppingCartService.getCartItemsByUserId(1L))
                .thenReturn(Collections.singletonList(item));

        mockMvc.perform(get("/cart/items")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].productId").value(101L));
    }

    @Test
    void testAddToCart_Success() throws Exception {
        AddCartRequest request = new AddCartRequest(1L, 101L, 2, new BigDecimal("99.99"));
        CartItem item = new CartItem();
        item.setProductId(101L);
        item.setQuantity(2);

        when(shoppingCartService.addProductToCart(any(), any(), any(), any()))
                .thenReturn(item);

        mockMvc.perform(post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.quantity").value(2));
    }

    @Test
    void testUpdateQuantity_Success() throws Exception {
        UpdateCartRequest request = new UpdateCartRequest(1L, 101L, 5, new BigDecimal("89.00"));
        CartItem updated = new CartItem();
        updated.setQuantity(5);

        when(shoppingCartService.updateProductQuantity(any(), any(), any(), any()))
                .thenReturn(updated);

        mockMvc.perform(put("/cart/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.quantity").value(5));
    }

    @Test
    void testRemoveFromCart_Success() throws Exception {
        mockMvc.perform(delete("/cart/remove")
                        .param("userId", "1")
                        .param("productId", "101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("商品已从购物车移除"));
    }

    @Test
    void testClearCart_Success() throws Exception {
        mockMvc.perform(delete("/cart/clear")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("购物车已清空"));
    }

}
