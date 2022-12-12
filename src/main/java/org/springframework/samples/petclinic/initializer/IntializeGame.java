package org.springframework.samples.petclinic.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.stereotype.Service;

@Service
public class IntializeGame {
    @Autowired
    InitializePositionService positionInitialiter;
    @Autowired
    GameService gameService;
    @Autowired
    InitializeMapService initializerService;


    public Game loadGame(Game game) {
        if(game.isNotLoaded()){
            game = initializerService.loadGameMap(game);
            
            // TODO load game deck from first and second halfdeck + initial decks + basics market cards



            game.setLoaded(true);
            gameService.save(game);
        }
        return game;
    }
}
