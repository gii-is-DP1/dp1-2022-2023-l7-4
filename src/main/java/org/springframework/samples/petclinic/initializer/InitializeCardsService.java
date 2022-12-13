package org.springframework.samples.petclinic.initializer;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardServiceRepo;
import org.springframework.samples.petclinic.checkers.Preconditions;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class InitializeCardsService {

	@Autowired
    private PlayerService playerService;

	@Autowired
	private CardServiceRepo cardService;
	
	@Autowired
	private GameService gameService;
    
    
    /**
	 * Cuando empieza una partida los jugadores empiezan con 
	 * 7 nobles y 3 soldados en su deck
	 * @param game
	 */
	public void initCardsInPlayers(Game game) {
		game.getPlayers().forEach(player -> initCardsInPlayer(game, player));
	}
		

	/**
	 * when game starts 6 cards go to sell Zone
	 * @param game
	 * @throws Exception
	 */
	public void initSellzoneInGame(@Valid Game game) throws Exception {
		List<Card> gameDeck = game.getGameDeck();
		Preconditions.check(gameDeck.size()>=6)
		.formattedError("can put initialize sellzone because there is less than 6 cards in deck:");
		for (int i = 0; i <= 6; i++) {
			int random = randomBetween(0,gameDeck.size());
			Card removed = gameDeck.remove(random);
			game.getSellZone().add(removed);

		}
		gameService.save(game);
	}

	



	//TODO Poner los Ids de noble y soldado
	private void initCardsInPlayer(Game game, Player player) {
		Card noble = cardService.getCardById(41);
		Card soldier = cardService.getCardById(42);

		List<Card> deck = Collections.nCopies(7, noble);
		deck.addAll(Collections.nCopies(3, soldier));

		player.setDeck(deck);
		playerService.savePlayer(player);
	}
	/**
	 * @return random int [min,max)
	 */
	private Integer randomBetween(Integer min, Integer max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
