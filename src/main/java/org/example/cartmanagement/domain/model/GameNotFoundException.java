package org.example.cartmanagement.domain.model;

import lombok.Getter;

@Getter
public class GameNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Game was not found in the database!";

    private final Long gameId;

    public GameNotFoundException(final Long gameId) {
        super(ERROR_MESSAGE);
        this.gameId = gameId;
    }

}
