package org.springframework.samples.petclinic.game;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.cardsMovement.PlayerMoveCardsService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.stereotype.Service;

@Service
public class EndTurnService {
    @Autowired
    PlayerMoveCardsService playerMoveCardsService;
    
    @Autowired
    GameService gameService;
    
    /**
     * <pre>Mueve las cartas de la mano y las cartas jugadas a el mazo de descartes.
     * Roba 5 cartas del mazo de robo -> si no quedan cartas en el mazo de robo se pasa el mazo de descarte a el mazo de robo.
     * Pasa al siguiente jugador y salva el game.</pre>
     * @param gameId
     */
    public void execute(Integer gameId){
        try {
            Game game=gameService.getGameById(gameId);
            game.setNotPromovableCards(new ArrayList<>());
            Player player=game.getCurrentPlayer();

            playerMoveCardsService.moveAllHandToDiscard(player);
            
            playerMoveCardsService.moveAllPlayedToDiscardPile(player);

            playerMoveCardsService.draw5CardsFromDeckToHand(player);

            gameService.nextPlayerAndSave(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
