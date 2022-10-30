package org.example.cartmanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GameDO {

    private long id;

    private String name;

    private String genre;

    private BigDecimal price;
}
