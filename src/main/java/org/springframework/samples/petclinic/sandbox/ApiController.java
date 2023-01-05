package org.springframework.samples.petclinic.sandbox;

import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final GameService gameService;

    public ApiController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("api/game/{gameId}")
    public Game getGameById(@PathVariable Integer gameId) {
        return gameService.getGameById(gameId);
    }

}