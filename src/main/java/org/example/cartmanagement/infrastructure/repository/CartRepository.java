package org.example.cartmanagement.infrastructure.repository;

import org.example.cartmanagement.infrastructure.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, Long> {
}
