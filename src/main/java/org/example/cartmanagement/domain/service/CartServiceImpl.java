package org.example.cartmanagement.domain.service;

import org.example.cartmanagement.domain.model.CartDO;
import org.example.cartmanagement.domain.model.GameDO;
import org.example.cartmanagement.infrastructure.consumer.ViewGameClientAdapter;
import org.example.cartmanagement.infrastructure.repository.CartRepositoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepositoryAdapter cartRepository;

    private final ViewGameClientAdapter viewGameClientAdapter;

    public CartServiceImpl(final CartRepositoryAdapter cartRepository, final ViewGameClientAdapter viewGameClientAdapter) {
        this.cartRepository = cartRepository;
        this.viewGameClientAdapter = viewGameClientAdapter;
    }

    @Override
    public void addToCart(final Long cartId, final Long gameId) {
        final GameDO game = viewGameClientAdapter.findById(gameId);
        logger.info("Game: {}", game);
        if (cartId == null) {
            createNewCart(gameId);
        } else {
            final Optional<CartDO> cartDoOptional = cartRepository.findById(cartId);
            cartDoOptional.ifPresentOrElse(cart -> {
                cart.getGamesIds().add(gameId);
                cartRepository.save(cart);
            }, () -> createNewCart(gameId));
        }
    }

    @Override
    public void removeFromCart() {

    }

    private void createNewCart(final long productId) {
        final CartDO cartDO = CartDO.builder()
                .gamesIds(List.of(productId))
                .build();
        cartRepository.save(cartDO);
    }
}
