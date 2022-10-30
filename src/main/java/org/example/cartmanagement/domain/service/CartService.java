package org.example.cartmanagement.domain.service;

import org.example.cartmanagement.domain.model.CartDO;

public interface CartService {

    long addToCart(final Long cartId, final Long gameId);

    void removeFromCart(long id, long gameId);

    void removeCart(long id);

    CartDO findById(long id);
}
