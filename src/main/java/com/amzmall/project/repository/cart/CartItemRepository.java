package com.amzmall.project.repository.cart;

import com.amzmall.project.domain.cart.CartItem;

import java.util.List;
import java.util.Optional;

public class CartItemRepository {
    public CartItem save(CartItem cartItem) {

    }

    public int countCartProduct(Long cartId) {
    }

    public Optional<CartItem> findById(Long cartItemId) {
    }

    public List<CartItem> findAllByCartIdAndIsDeletedIsFalse(Long cartId) {
    }
}
