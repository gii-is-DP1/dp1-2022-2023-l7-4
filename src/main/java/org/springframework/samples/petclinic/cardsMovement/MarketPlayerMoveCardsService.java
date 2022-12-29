package org.springframework.samples.petclinic.cardsMovement;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.checkers.CheckCardMovement;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class MarketPlayerMoveCardsService {
    
    @Autowired
    private PlayerService playerService;

	@Autowired
	private GameService gameService;

    @Autowired
    MarketMoveCardsService marketMoveCardsService;

    /**
     * takes a selected card form market and inserts into players hand
     * checks if card is in sellzone and if player has enough influence 
     * @param card
     * @param game
     * @param player
     * @throws Exception if card is not in sellzone, if player has not enough influence
     */
    public void buyCard(Card card, Player player) throws Exception{
        Game game = player.getGame();
        int playerInfluence =player.getInfluence();
        int cardCost= card.getCost();

        CheckCardMovement.sellZoneContainsCard(game.getSellZone(),card);
        CheckCardMovement.playerHasEnoughInfluenceToBuyCard(playerInfluence,cardCost);
 
        moveSelectedCard(card, game.getSellZone(), player.getDiscardPile(), player);
        marketMoveCardsService.moveFromGameDeckToSellZone(game);
        player.setInfluence(playerInfluence-cardCost);
        playerService.savePlayer(player);
        gameService.save(game);
    }



    // private methods
    private void moveSelectedCard(Card card, List<Card> source, List<Card> target, @Valid Player player) {
        source.remove(card);
        target.add(card);
    }










}
