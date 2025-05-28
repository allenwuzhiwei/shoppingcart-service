package com.iss.shoppingcart.service.impl;

import com.iss.shoppingcart.entity.CartItem;
import com.iss.shoppingcart.entity.ShoppingCart;
import com.iss.shoppingcart.repository.CartItemRepository;
import com.iss.shoppingcart.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetShoppingCartByUserId_Found() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(1L);
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        Optional<ShoppingCart> result = shoppingCartService.getShoppingCartByUserId(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getUserId());
    }

    @Test
    void testGetCartItemsByUserId() {
        CartItem item = new CartItem();
        item.setUserId(1L);
        when(cartItemRepository.findByUserId(1L)).thenReturn(Collections.singletonList(item));

        List<CartItem> result = shoppingCartService.getCartItemsByUserId(1L);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void testAddProductToCart_NewCartAndItem() {
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(cartItemRepository.findByUserIdAndProductId(1L, 101L)).thenReturn(Optional.empty());

        CartItem savedItem = new CartItem();
        savedItem.setUserId(1L);
        savedItem.setProductId(101L);
        savedItem.setQuantity(2);
        when(cartItemRepository.save(any())).thenReturn(savedItem);

        CartItem result = shoppingCartService.addProductToCart(1L, 101L, 2, BigDecimal.valueOf(50.00));
        assertEquals(1L, result.getUserId());
        assertEquals(101L, result.getProductId());
    }

    @Test
    void testAddProductToCart_ExistingItem() {
        ShoppingCart cart = new ShoppingCart();
        when(shoppingCartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));

        CartItem existingItem = new CartItem();
        existingItem.setUserId(1L);
        existingItem.setProductId(101L);
        existingItem.setQuantity(1);
        when(cartItemRepository.findByUserIdAndProductId(1L, 101L)).thenReturn(Optional.of(existingItem));

        CartItem savedItem = new CartItem();
        savedItem.setUserId(1L);
        savedItem.setProductId(101L);
        savedItem.setQuantity(3);
        when(cartItemRepository.save(any())).thenReturn(savedItem);

        CartItem result = shoppingCartService.addProductToCart(1L, 101L, 2, BigDecimal.valueOf(20.00));
        assertEquals(3, result.getQuantity());
    }

    @Test
    void testUpdateProductQuantity_Success() {
        CartItem item = new CartItem();
        item.setUserId(1L);
        item.setProductId(101L);
        item.setQuantity(1);
        when(cartItemRepository.findByUserIdAndProductId(1L, 101L)).thenReturn(Optional.of(item));
        when(cartItemRepository.save(any())).thenReturn(item);

        CartItem result = shoppingCartService.updateProductQuantity(1L, 101L, 3, BigDecimal.valueOf(10));
        assertEquals(3, result.getQuantity());
        assertEquals(BigDecimal.valueOf(30), result.getPriceAtAdd());
    }

    @Test
    void testRemoveProductFromCart_Success() {
        doNothing().when(cartItemRepository).deleteByUserIdAndProductId(1L, 101L);
        shoppingCartService.removeProductFromCart(1L, 101L);
        verify(cartItemRepository, times(1)).deleteByUserIdAndProductId(1L, 101L);
    }

    @Test
    void testClearCart_Success() {
        CartItem item = new CartItem();
        item.setUserId(1L);
        when(cartItemRepository.findByUserId(1L)).thenReturn(Collections.singletonList(item));
        doNothing().when(cartItemRepository).deleteAll(anyList());

        shoppingCartService.clearCart(1L);
        verify(cartItemRepository, times(1)).deleteAll(anyList());
    }
}
