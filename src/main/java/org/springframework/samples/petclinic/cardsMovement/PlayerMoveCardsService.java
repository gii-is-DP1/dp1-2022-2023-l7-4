package org.springframework.samples.petclinic.cardsMovement;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.checkers.CheckCardMovement;
import org.springframework.samples.petclinic.checkers.Preconditions;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class PlayerMoveCardsService {

	@Autowired
    private PlayerService playerService; //USED TO SAVE UPDATED LISTS
	



	public void draw5CardsFromDeckToHand(@Valid Player player) throws Exception{
		drawFromDeckToHand(5, player);
	}
	
	public void drawFromDeckToHand(Integer numberOfCards,Player player) throws Exception{
			for (int i = 0; i < numberOfCards; i++) {
				if(player.getDeck().isEmpty()){
					moveAllDiscardedToDeck(player);
				}
				int random = randomBetween(0, player.getDeck().size()-1);
				Card removed = player.getDeck().remove(random);
				player.getHand().add(removed);
			}
		playerService.savePlayer(player);
	}

	public void moveFromHandToPlayed(@Valid Card card,@Valid Player player){
		moveSelectedCardAndSave(card,player.getHand(), player.getPlayed(), player);
	}



//	MOVE FULL COLLECTIONS

	public void moveAllPlayedToDiscardPile(@Valid Player player) throws Exception{
		moveAllAndSave(player.getPlayed(), player.getDiscarded(), player);
	}

	public void moveAllDiscardedToDeck(@Valid Player player) throws Exception{
		moveAllAndSave(player.getDiscarded(), player.getDeck(), player);
	}

	public void moveAllHandToDiscard(@Valid Player player) throws Exception{
		moveAllAndSave(player.getHand(), player.getDiscarded(), player);
	}

//	PROMOTE

	public void promoteSelectedFromPlayed(@Valid Card card, @Valid Player player){
		moveSelectedCardAndSave(card, player.getPlayed(), player.getInnerCircle(), player);
	}

	public void promoteSelectedFromDiscardPile(@Valid Card card,@Valid Player player){
		moveSelectedCardAndSave(card,player.getDiscarded(),player.getInnerCircle(),  player);
	}

	public void promoteSelectedFromDeck(@Valid Card card,@Valid Player player){
		moveSelectedCardAndSave(card, player.getDeck(), player.getInnerCircle(), player);
	}
	




	
//	privates methods (Don't touch) -----------------------------------------------------------------------------


	

	private void moveSelectedCardAndSave(Card card, List<Card> source, List<Card> target, Player player) {
		source.remove(card);
		target.add(card);
		playerService.savePlayer(player);
	}



	private void moveAllAndSave(List<Card> source, List<Card> target, Player player) throws Exception{
		Integer size = source.size();
		for (int i = size-1; i >= 0; i--) {
			Card removed = source.remove(i);
			target.add(removed);
			playerService.savePlayer(player);
		}
	}


	private Integer randomBetween(Integer min, Integer max) {
		return (int) ((Math.random() * (max - min)) + min);
	}


}