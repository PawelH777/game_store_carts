package org.example.cartmanagement.domain.model;

import lombok.Getter;

@Getter
public class GameAlreadyInCartException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Game already is in cart!";

    private final Long gameId;

    public GameAlreadyInCartException(final Long gameId) {
        super(ERROR_MESSAGE);
        this.gameId = gameId;
    }
}
