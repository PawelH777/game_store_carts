package org.example.cartmanagement.domain.model;

import lombok.Getter;

@Getter
public class CartNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Cart was not found in the database!";

    private final Long cartId;

    public CartNotFoundException(final Long cartId) {
        super(ERROR_MESSAGE);
        this.cartId = cartId;
    }
}
