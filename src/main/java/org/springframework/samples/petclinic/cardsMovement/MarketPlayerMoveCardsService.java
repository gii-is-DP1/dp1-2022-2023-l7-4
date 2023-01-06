package org.springframework.samples.petclinic.cardsMovement;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardService;
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

    @Autowired
    CardService cardService;

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

        CheckCardMovement.sellZoneLolthsGuardsContainsCard(game.getSellZone(),game.getLolths(), game.getHouseGuards(),card);
        CheckCardMovement.playerHasEnoughInfluenceToBuyCard(playerInfluence,cardCost);
 
        if (game.getSellZone().contains(card)){
            Integer index = game.getSellZone().indexOf(card);
            moveOneCardFromSellZoneToDiscarded(card, game, player);
            marketMoveCardsService.moveFromGameDeckToSellZone(game,index);
        }
        else if (game.getLolths().contains(card)){
            moveOneCardFromLolthsToDiscarded(card, game, player);
        }
        else if (game.getHouseGuards().contains(card)){
            moveOneCardFromGuardsToDiscarded(card, game, player);
        }
        
        player.setInfluence(playerInfluence-cardCost);
        playerService.savePlayer(player);
        gameService.save(game);
    }



    // private methods
    private void moveOneCardFromSellZoneToDiscarded(Card card, Game game, @Valid Player player) {
        List<Card> sellZone = game.getSellZone();
        List<Card> discarded = player.getDiscarded();
        if (game.getGameDeck().isEmpty()){
            sellZone.add(sellZone.indexOf(card), cardService.getCardById(0));
        } 
        sellZone.remove(card);
        discarded.add(card);
    }

    private void moveOneCardFromLolthsToDiscarded(Card card, Game game, @Valid Player player) {
        List<Card> lolths = game.getLolths();
        List<Card> discarded = player.getDiscarded();
        
        lolths.remove(card);
        discarded.add(card);
    }

    private void moveOneCardFromGuardsToDiscarded(Card card, Game game, @Valid Player player) {
        List<Card> guards = game.getHouseGuards();
        List<Card> discarded = player.getDiscarded();
        
        guards.remove(card);
        discarded.add(card);
    }










}
