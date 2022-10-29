package org.example.cartmanagement.domain.service;

import org.example.cartmanagement.domain.model.CartDO;
import org.example.cartmanagement.infrastructure.repository.CartRepositoryAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepositoryAdapter cartRepository;

    public CartServiceImpl(final CartRepositoryAdapter cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void addToCart(final Long cartId, final Long gameId) {
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
