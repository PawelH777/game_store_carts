package org.example.cartmanagement.infrastructure.consumer;

import org.example.cartmanagement.infrastructure.consumer.model.GameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("view-games")
public interface ViewGameClient {

    @GetMapping("/games/{id}")
    ResponseEntity<GameDTO> findById(@PathVariable final long id);
}
