package org.example.cartmanagement.infrastructure.consumer;

import org.example.cartmanagement.domain.model.GameDO;

public interface ViewGameClientAdapter {

    GameDO findById(final long id);
}
