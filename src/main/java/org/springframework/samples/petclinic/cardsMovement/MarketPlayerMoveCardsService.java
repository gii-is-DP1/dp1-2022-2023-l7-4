package org.springframework.samples.petclinic.cardsMovement;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardServiceRepo;
import org.springframework.samples.petclinic.checkers.CheckCardMovement;
import org.springframework.samples.petclinic.checkers.Preconditions;
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
        
        
        game.getSellZone().remove(card);
        player.getHand().add(card);
        player.setInfluence(playerInfluence-cardCost);
        gameService.saveGame(game);
        playerService.savePlayer(player);
    }










}
