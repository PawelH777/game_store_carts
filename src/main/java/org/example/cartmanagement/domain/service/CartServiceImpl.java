package org.example.cartmanagement.domain.service;

import org.example.cartmanagement.domain.model.*;
import org.example.cartmanagement.infrastructure.consumer.ViewGameClientAdapter;
import org.example.cartmanagement.infrastructure.repository.CartRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepositoryAdapter cartRepositoryAdapter;

    private final ViewGameClientAdapter viewGameClientAdapter;

    public CartServiceImpl(final CartRepositoryAdapter cartRepositoryAdapter, final ViewGameClientAdapter viewGameClientAdapter) {
        this.cartRepositoryAdapter = cartRepositoryAdapter;
        this.viewGameClientAdapter = viewGameClientAdapter;
    }

    @Override
    public long addToCart(final Long id, final Long gameId) {
        if (id == null) {
            return createNewCart(gameId);
        } else {
            final Optional<CartDO> cartDoOptional = cartRepositoryAdapter.findById(id);
            return cartDoOptional.map(cartDO -> addGameToCart(gameId, cartDO))
                    .orElseGet(() -> createNewCart(gameId));
        }
    }

    @Override
    public void removeFromCart(final long id, final long gameId) {
        final Optional<CartDO> cartDoOptional = cartRepositoryAdapter.findById(id);
        cartDoOptional.ifPresent(cart -> removeFromCart(gameId, cart));
    }

    @Override
    public void removeCart(final long id) {
        final Optional<CartDO> cartDoOptional = cartRepositoryAdapter.findById(id);
        cartDoOptional.ifPresent(cart -> cartRepositoryAdapter.deleteById(cart.getId()));
    }

    @Override
    public CartDO findById(final long id) {
        final Optional<CartDO> cartDoOptional = cartRepositoryAdapter.findById(id);

        return cartDoOptional.orElseThrow(() -> {
            throw new CartNotFoundException(id);
        });
    }

    private long createNewCart(final long gameId) {
        final Optional<GameDO> gameOptional = viewGameClientAdapter.findById(gameId);

        if (gameOptional.isEmpty()) {
            throw new GameNotFoundException(gameId);
        }

        final CartDO cartDO = CartDO.builder()
                .gamesIds(List.of(gameId))
                .totalPrice(gameOptional.get().getPrice())
                .build();
        return cartRepositoryAdapter.save(cartDO);
    }

    private long addGameToCart(final long gameId, final CartDO cart) {
        if (cart.getGamesIds().contains(gameId)) {
            throw new GameAlreadyInCartException(gameId);
        }

        cart.getGamesIds().add(gameId);
        cart.setTotalPrice(calculateCartTotal(cart.getGamesIds()));
        return cartRepositoryAdapter.save(cart);
    }

    private void removeFromCart(final Long gameId, final CartDO cart) {
        cart.getGamesIds().remove(gameId);

        if (cart.getGamesIds().isEmpty()) {
            cartRepositoryAdapter.deleteById(cart.getId());
            return;
        }

        cart.setTotalPrice(calculateCartTotal(cart.getGamesIds()));
        cartRepositoryAdapter.save(cart);
    }

    private BigDecimal calculateCartTotal(final List<Long> gameIds) {
        BigDecimal cartTotal = BigDecimal.ZERO;
        for (final Long gameId : gameIds) {
            final Optional<GameDO> gameOptional = viewGameClientAdapter.findById(gameId);

            if (gameOptional.isEmpty()) {
                throw new GameNotFoundException(gameId);
            }

            cartTotal = cartTotal.add(gameOptional.get().getPrice());
        }
        return cartTotal;
    }
}
