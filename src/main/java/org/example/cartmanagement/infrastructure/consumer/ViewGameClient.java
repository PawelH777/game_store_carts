package org.example.cartmanagement.infrastructure.consumer;

import org.example.cartmanagement.infrastructure.consumer.model.GameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("view")
public interface ViewGameClient {

    @GetMapping("/api/{id}")
    ResponseEntity<GameDTO> findById(@PathVariable final long id);
}
