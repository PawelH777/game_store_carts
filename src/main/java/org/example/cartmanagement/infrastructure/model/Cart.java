package org.example.cartmanagement.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Document(collection = "carts")
public class Cart {

    @Transient
    public static final String SEQUENCE_NAME = "carts_sequence";

    @Id
    private long id;

    private List<Long> gamesIds;

    private BigDecimal totalPrice;
}
