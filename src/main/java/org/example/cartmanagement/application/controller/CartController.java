package org.example.cartmanagement.application.controller;

import org.example.cartmanagement.application.model.CartRequest;
import org.example.cartmanagement.domain.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    ResponseEntity<Object> addToCart(@RequestBody final CartRequest cartRequest) {
        if (cartRequest.getGameId() == null) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Product ID needs to be provided");
        }

        cartService.addToCart(cartRequest.getId(), cartRequest.getGameId());
        return ResponseEntity.status(200).build();
    }

    void removeFromCart(final long id) {

        cartService.removeFromCart();
    }
}
