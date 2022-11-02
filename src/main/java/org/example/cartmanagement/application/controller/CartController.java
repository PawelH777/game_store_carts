package org.example.cartmanagement.application.controller;

import org.example.cartmanagement.application.model.AddToCartRequest;
import org.example.cartmanagement.application.model.CartDTO;
import org.example.cartmanagement.domain.model.CartDO;
import org.example.cartmanagement.domain.model.CartNotFoundException;
import org.example.cartmanagement.domain.model.GameAlreadyInCartException;
import org.example.cartmanagement.domain.model.GameNotFoundException;
import org.example.cartmanagement.domain.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CartController {

    Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    ResponseEntity<Long> addToCart(@RequestBody final AddToCartRequest addToCartRequest) {
        if (addToCartRequest.getGameId() == null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Game ID needs to be provided");
        }

        final long cartId;
        try {
            cartId = cartService.addToCart(addToCartRequest.getId(), addToCartRequest.getGameId());
        } catch (final GameNotFoundException ex) {
            logger.error("Game with game id {} was not found in database, error stacktrace: ", ex.getGameId(), ex);
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Game with id " + ex.getGameId() + " was not found in database");
        } catch (final GameAlreadyInCartException ex) {
            logger.error("Game with game id {} is already in cart, error stacktrace: ", ex.getGameId(), ex);
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Game with id " + ex.getGameId() + " is already in cart");
        }

        return ResponseEntity.status(200)
                .body(cartId);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> removeCart(@PathVariable final long id) {

        cartService.removeCart(id);

        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}/{gameId}")
    ResponseEntity<Object> removeFromCart(@PathVariable final long id, @PathVariable final long gameId) {
        try {
            cartService.removeFromCart(id, gameId);
        } catch (final GameNotFoundException ex) {
            logger.error("Game with game id {} was not found in database, error stacktrace: ", ex.getGameId(), ex);
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Game with id " + ex.getGameId() + " was not found in database");
        }

        return ResponseEntity.status(200).build();
    }

    @GetMapping("/{id}")
    ResponseEntity<CartDTO> findById(@PathVariable final long id) {
        final CartDO cart;
        try {
            cart = cartService.findById(id);
        } catch (final CartNotFoundException ex) {
            logger.error("Cart with cart id {} was not found in database, error stacktrace: ", ex.getCartId(), ex);
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Cart with id " + ex.getCartId() + " was not found in database");
        }
        return ResponseEntity.status(200)
                .body(buildCartDTO(cart));
    }

    private CartDTO buildCartDTO(final CartDO cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .gamesIds(cart.getGamesIds())
                .totalPrice(cart.getTotalPrice())
                .build();
    }
}
