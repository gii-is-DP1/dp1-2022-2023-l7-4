package org.springframework.samples.petclinic.card.action.executeActions;

import org.springframework.samples.petclinic.card.action.Action;
import org.springframework.samples.petclinic.cardsMovement.PlayerMoveCardsService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;

public class AutomaticActions {

    public static void earnInfluence(Game game, Action action){
        Player player = game.getCurrentPlayer();
        player.earnInfluence(action.getValue());

    }

    public static void earnPower(Game game, Action action){
        Player player = game.getCurrentPlayer();
        player.earnPower(action.getValue());
    }

    

}
