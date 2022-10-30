package org.example.cartmanagement.infrastructure.repository;

import org.example.cartmanagement.domain.model.CartDO;

import java.util.Optional;

public interface CartRepositoryAdapter {

    long save(CartDO cartDO);

    Optional<CartDO> findById(long id);

    void deleteById(long id);
}
