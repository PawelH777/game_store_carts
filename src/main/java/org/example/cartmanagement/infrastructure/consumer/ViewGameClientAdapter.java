package org.example.cartmanagement.infrastructure.consumer;

import org.example.cartmanagement.domain.model.GameDO;

import java.util.Optional;

public interface ViewGameClientAdapter {

    Optional<GameDO> findById(final long id);
}
