package org.springframework.samples.petclinic.cardsMovement;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.stereotype.Service;
@Service
public class MarketMoveCardsService {
    @Autowired
	private GameService gameService;


    public void moveFromGameDeckToSellZone(@Valid Card card,@Valid Game game){
        List<Card> gameDeck = game.getGameDeck();
        if(gameDeck.isEmpty()){
            // TODO end of game in the next round. Turn.conditionToFinish = true
        } else{
        moveCardAndSave(card,gameDeck,game.getSellZone(),game);   
        }   
    }

    public void devourCardFromSellZone(@Valid Card card,@Valid Game game){
        moveCardAndSave(card,game.getSellZone(),game.getDevoured(),game);
        moveFromGameDeckToSellZone(card, game);
    }
    
    
    private void moveCardAndSave(Card card, List<Card> source, List<Card> target, @Valid Game game) {
        source.remove(card);
        target.add(card);
        gameService.saveGame(game);
    }
}
