package org.springframework.samples.petclinic.cardsMovement;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.stereotype.Service;
@Service
public class MarketMoveCardsService {
    @Autowired
	private GameService gameService;



    public void moveFromGameDeckToSellZone(@Valid Game game, Integer index){// if game deck is empty the game will finish in the next round
        List<Card> gameDeck = game.getGameDeck();
        if(gameDeck.size()>0){

            Card card = gameDeck.get(randomBetween(0,gameDeck.size()-1));
            moveSelectedCardAndSave(card,gameDeck,game.getSellZone(),game, index);   
        }
    }

    public void devourCardFromSellZone(@Valid Card card,@Valid Game game){
        Integer index = game.getSellZone().indexOf(card);
        moveSelectedCardAndSave(card,game.getSellZone(),game.getDevoured(),game, game.getDevoured().size());
        moveFromGameDeckToSellZone(game, index);
    }
    
    
    private void moveSelectedCardAndSave(Card card, List<Card> source, List<Card> target, @Valid Game game, Integer index) {
        source.remove(card);
        target.add(index, card);
        gameService.save(game);
    }
    
    private Integer randomBetween(Integer min, Integer max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
