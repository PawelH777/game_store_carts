package org.example.cartmanagement.infrastructure.consumer;

import org.example.cartmanagement.domain.model.GameDO;
import org.example.cartmanagement.infrastructure.consumer.model.GameDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ViewGameClientAdapterImpl implements ViewGameClientAdapter {

    private final ViewGameClient viewGameClient;

    public ViewGameClientAdapterImpl(final ViewGameClient viewGameClient) {
        this.viewGameClient = viewGameClient;
    }

    @Override
    public Optional<GameDO> findById(final long id) {
        final ResponseEntity<GameDTO> res = viewGameClient.findById(id);
        if (!res.getStatusCode().is2xxSuccessful()) {
            return Optional.empty();
        }
        return res.getBody() != null ? buildGame(res.getBody()) : Optional.empty();
    }

    private Optional<GameDO> buildGame(final GameDTO body) {
        final GameDO game = GameDO.builder()
                .id(body.getId())
                .name(body.getName())
                .genre(body.getGenre())
                .price(body.getPrice())
                .build();
        return Optional.of(game);
    }
}
