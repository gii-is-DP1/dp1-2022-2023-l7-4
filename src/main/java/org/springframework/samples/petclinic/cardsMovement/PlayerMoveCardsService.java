package org.springframework.samples.petclinic.cardsMovement;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.checkers.CheckCardMovement;
import org.springframework.samples.petclinic.checkers.Preconditions;
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
	
	public void drawFromDeckToHand(Integer numberOfCard,Player player) throws Exception{
		if(player.getDeck().size()<numberOfCard){
			moveAllDiscardedToDeck(player);
		}
		moveNCardsRandomAndSave(numberOfCard, player.getDeck(), player.getHand(), player);
	}

	public void moveFromHandToPlayed(@Valid Card card,@Valid Player player){
		moveSelectedCardAndSave(card,player.getHand(), player.getPlayed(), player);
	}



//	MOVE FULL COLLECTIONS

	public void moveAllPlayedToDiscardPile(@Valid Player player) throws Exception{
		moveAllAndSave(player.getPlayed(), player.getDiscardPile(), player);
	}

	public void moveAllDiscardedToDeck(@Valid Player player) throws Exception{
		moveAllAndSave(player.getDiscardPile(), player.getDeck(), player);
	}

	public void moveAllHandToDiscard(@Valid Player player) throws Exception{
		moveAllAndSave(player.getHand(), player.getDiscardPile(), player);
	}

//	PROMOTE

	public void promoteSelectedFromPlayed(@Valid Card card, @Valid Player player){
		moveSelectedCardAndSave(card, player.getPlayed(), player.getInnerCircle(), player);
	}

	public void promoteSelectedFromDiscardPile(@Valid Card card,@Valid Player player){
		moveSelectedCardAndSave(card,player.getDiscardPile(),player.getInnerCircle(),  player);
	}
	




	
//	privates methods (Don't touch) -----------------------------------------------------------------------------


	

	private void moveSelectedCardAndSave(Card card, List<Card> source, List<Card> target, Player player) {
		source.remove(card);
		target.add(card);
		playerService.savePlayer(player);
	}



	private void moveAllAndSave(List<Card> source, List<Card> target, Player player) throws Exception{
		CheckCardMovement.sourceIsNotEmpty(source);
		for (int i = 0; i < source.size(); i++) {
			Card removed = source.remove(i);
			target.add(removed);
		}
	}


	/**
	 * @param numberOfCards number of random cards to draw
	 * @param source list of cards (origin)
	 * @param target list of cards (card goes here)
	 * @throws Exception null/empty source, selected more cards than cards in list
	 */
	private void moveNCardsRandomAndSave(Integer numberOfCards,List<Card> source, List<Card> target, Player player) throws Exception {

		CheckCardMovement.sourceIsNotEmpty(source);
		Preconditions.check(numberOfCards<=source.size()).error("cant take more cards than cards in given list");
			for (int i = 0; i < numberOfCards; i++) {
				int random = randomBetween(0, numberOfCards);
				Card removed = source.remove(random);
				target.add(removed);
			}
		
		playerService.savePlayer(player);
	}

	private Integer randomBetween(Integer min, Integer max) {
		return (int) ((Math.random() * (max - min)) + min);
	}


}