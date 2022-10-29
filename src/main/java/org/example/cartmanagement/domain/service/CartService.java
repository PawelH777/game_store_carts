package org.example.cartmanagement.domain.service;

public interface CartService {


    void addToCart(final Long cartId, final Long gameId);

    void removeFromCart();
}
