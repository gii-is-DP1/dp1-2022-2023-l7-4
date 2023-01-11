package org.springframework.samples.tyrantsOfTheUnderdark.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.stereotype.Service;

@Service
public class IntializeGame {
    @Autowired
    InitializePositionService positionInitialiter;
    @Autowired
    GameService gameService;
    @Autowired
    InitializeMapService mapInitializer;
    @Autowired
    InitializeCardsService cardIntializer;


    public Game loadGame(Game game) {
        try {
            if (game.isNotLoaded()) {
                game = mapInitializer.loadGameMap(game);

                game = cardIntializer.loadCards(game);

                game.setLoaded(true);
                gameService.save(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    public void loadAutomaticWhitePositions(Game game){
        positionInitialiter.setWhiteTroopsPositions(game);
        gameService.save(game);
    }
}
