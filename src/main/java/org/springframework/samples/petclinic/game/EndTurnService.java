package org.springframework.samples.petclinic.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.card.action.ActionService;
import org.springframework.samples.petclinic.cardsMovement.PlayerMoveCardsService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class EndTurnService {
    @Autowired
    PlayerMoveCardsService playerMoveCardsService;
    
    @Autowired
    GameService gameService;
    @Autowired
    ActionService actionService;



    @Autowired
    PlayerService playerService;
    
    /**
     * <pre>Mueve las cartas de la mano y las cartas jugadas a el mazo de descartes.
     * Roba 5 cartas del mazo de robo -> si no quedan cartas en el mazo de robo se pasa el mazo de descarte a el mazo de robo.
     * Pasa al siguiente jugador y salva el game.</pre>
     * @param gameId
     */
    public void execute(Game game){
        try {

            Player player=game.getCurrentPlayer();
            Action currentAction=game.getCurrentAction();
            if(currentAction!=null){
                game.setCurrentAction(null);
                actionService.remove(currentAction);
            }

            playerMoveCardsService.moveAllHandToDiscard(player);
            
            playerMoveCardsService.moveAllPlayedToDiscardPile(player);

            playerMoveCardsService.draw5CardsFromDeckToHand(player);
            for(City city:game.getCities()){
                if(city.whoTotallyControls() !=null && city.whoTotallyControls().equals(player))
                 player.setMarkerVP(player.getMarkerVP()+city.getVpControlled());
            }
            this.playerService.savePlayer(player);
            
            gameService.nextPlayerAndSave(game);

            Player nextPlayer=game.getCurrentPlayer();
		    for(City city:game.getCities()){
			    if((city.whoControls() !=null && city.whoControls().equals(nextPlayer))
                 || (city.whoTotallyControls()!=null && city.whoTotallyControls().equals(nextPlayer))){
                    nextPlayer.earnInfluence(city.getInfluenceTotalControlled());
                }
		    }
		    this.playerService.savePlayer(nextPlayer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
