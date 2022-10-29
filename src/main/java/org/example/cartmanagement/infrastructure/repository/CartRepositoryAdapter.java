package org.example.cartmanagement.infrastructure.repository;

import org.example.cartmanagement.domain.model.CartDO;

import java.util.Optional;

public interface CartRepositoryAdapter {

    void save(CartDO cartDO);

    Optional<CartDO> findById(long id);
}
