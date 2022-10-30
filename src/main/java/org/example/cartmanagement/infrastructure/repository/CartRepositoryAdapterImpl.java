package org.example.cartmanagement.infrastructure.repository;

import org.example.cartmanagement.domain.model.CartDO;
import org.example.cartmanagement.infrastructure.config.MongodbSequenceGenerator;
import org.example.cartmanagement.infrastructure.model.Cart;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartRepositoryAdapterImpl implements CartRepositoryAdapter {

    private final CartRepository cartRepository;

    private final MongodbSequenceGenerator sequenceGenerator;

    public CartRepositoryAdapterImpl(final CartRepository cartRepository, final MongodbSequenceGenerator sequenceGenerator) {
        this.cartRepository = cartRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public long save(final CartDO cartDO) {
        final Cart cart = cartRepository.save(buildCart(cartDO));
        return cart.getId();
    }

    @Override
    public Optional<CartDO> findById(final long id) {
        final Optional<Cart> cartOptional = cartRepository.findById(id);

        return cartOptional.map(this::buildCart);
    }

    @Override
    public void deleteById(final long id) {
        cartRepository.deleteById(id);
    }

    private Cart buildCart(final CartDO cartDO) {
        final long id = cartDO.getId() != null ? cartDO.getId() : sequenceGenerator.generateSequence(Cart.SEQUENCE_NAME);
        return Cart.builder()
                .id(id)
                .gamesIds(cartDO.getGamesIds())
                .totalPrice(cartDO.getTotalPrice())
                .build();
    }

    private CartDO buildCart(final Cart cart) {
        return CartDO.builder()
                .id(cart.getId())
                .gamesIds(cart.getGamesIds())
                .totalPrice(cart.getTotalPrice())
                .build();
    }
}
