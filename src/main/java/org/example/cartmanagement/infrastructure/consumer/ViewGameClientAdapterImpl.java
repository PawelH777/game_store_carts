package org.example.cartmanagement.infrastructure.consumer;

import org.example.cartmanagement.domain.model.GameDO;
import org.example.cartmanagement.infrastructure.consumer.model.GameDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ViewGameClientAdapterImpl implements ViewGameClientAdapter {

    private final ViewGameClient viewGameClient;

    public ViewGameClientAdapterImpl(final ViewGameClient viewGameClient) {
        this.viewGameClient = viewGameClient;
    }

    @Override
    public GameDO findById(final long id) {
        final ResponseEntity<GameDTO> res = viewGameClient.findById(id);
        if (!res.getStatusCode().is2xxSuccessful()) {
            return null;
        }
        return buildGame(Objects.requireNonNull(res.getBody()));
    }

    private GameDO buildGame(final GameDTO body) {
        return GameDO.builder()
                .id(body.getId())
                .name(body.getName())
                .genre(body.getGenre())
                .price(body.getPrice())
                .build();
    }
}
