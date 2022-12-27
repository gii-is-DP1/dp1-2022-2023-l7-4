package org.springframework.samples.petclinic.initializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardServiceRepo;
import org.springframework.samples.petclinic.cardsMovement.PlayerMoveCardsService;
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

	@Autowired
	private PlayerMoveCardsService playerMoveCardsService;
    

	/**
	 * <pre> A cada jugador le asigna sus cartas iniciales y roba 5 aleatorias.
	 * Añade al mazo de mercado los 2 halfDecks seleccionados en la creación de partida.
	 * Coge 6 cartas aleatorias del mazo y las pone en la Sell Zone del juego.
	 * Añade 15 guardias y 15 lolth a sus respectivas zonas del mercado.
	 * Guarda el juego en base de datos.</pre>
	 * @param game
	 * @return
	 * @throws Exception
	 */
	public Game loadCards(Game game) throws Exception {
		initCardsInPlayers(game);
		initGameDecks(game);
		initSellzoneInGame(game);
		initLolthAndGuards(game);

		gameService.save(game);

		return gameService.getGameById(game.getId());
}
    
    /**
	 * Cuando empieza una partida los jugadores empiezan con 
	 * 7 nobles y 3 soldados en su deck
	 * @param game
     * @throws Exception
	 */
	private void initCardsInPlayers(Game game) throws Exception {
		for (Player player:game.getPlayers()){
			initCardsInPlayer(game, player);
		}
	}
		
	private void initCardsInPlayer(Game game, Player player) throws Exception {
		Card soldier = cardService.getCardById(1);
		Card noble = cardService.getCardById(2);

		List<Card> deck = new ArrayList<>(Collections.nCopies(7, noble));
		List<Card> soldiers= Collections.nCopies(3, soldier);
		deck.addAll(soldiers);

		player.setDeck(deck);

		playerMoveCardsService.draw5CardsFromDeckToHand(player);

		playerService.savePlayer(player);
	}


	private void initGameDecks(@Valid Game game) throws Exception {
		List<Card> gameDeck= new ArrayList<>();
		gameDeck.addAll(cardService.getCardsByHalfdeck(game.getFirstHalfDeck()));
		gameDeck.addAll(cardService.getCardsByHalfdeck(game.getSecondHalfDeck()));

		game.setGameDeck(gameDeck);
	}

	/**
	 * when game starts 6 cards go to sell Zone
	 * @param game
	 * @throws Exception
	 */
	private void initSellzoneInGame(@Valid Game game) throws Exception {
		List<Card> gameDeck = game.getGameDeck();
		Preconditions.check(gameDeck.size()>=6)
		.formattedError("can put initialize sellzone because there is less than 6 cards in deck:");
		for (int i = 1; i <= 6; i++) {
			int random = randomBetween(0,gameDeck.size());
			Card removed = gameDeck.remove(random);
			game.getSellZone().add(removed);
		}
		gameService.save(game);
	}

	private void initLolthAndGuards(Game game) throws Exception {
		Card lolth = cardService.getCardById(3);
		Card guards = cardService.getCardById(4);

		List<Card> lolthsDeck = new ArrayList<>(Collections.nCopies(15, lolth));
		List<Card> guardsDeck = new ArrayList<>(Collections.nCopies(15, guards));

		game.setLolths(lolthsDeck);
		game.setHouseGuards(guardsDeck);
	}


	/**
	 * @return random int [min,max)
	 */
	private Integer randomBetween(Integer min, Integer max) {
		return (int) ((Math.random() * (max - min)) + min);
	}


	
}
