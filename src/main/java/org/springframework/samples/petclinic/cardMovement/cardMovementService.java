package org.springframework.samples.petclinic.cardMovement;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardServiceRepo;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class cardMovementService {

	@Autowired
    private PlayerService playerService;

	@Autowired
	private CardServiceRepo cardService;
	
	@Autowired
	private GameService gameService;

	public void initCardsInPlayers(Game game) {
		game.getPlayers().forEach(player -> initCardsInPlayer(game, player));
	}

	private void initCardsInPlayer(Game game, Player player) {
		Card noble = cardService.getCardById(41);
		Card soldier = cardService.getCardById(42);

		List<Card> deck = Collections.nCopies(7, noble);
		deck.addAll(Collections.nCopies(3, soldier));

		player.setDeck(deck);
		playerService.savePlayer(player);
	}

	/**
	 * Esta funcion elimina de la lista origen(source) y la añade a la lista destino(target).
	 * HACER SAVE de los objetos que contengan las listas después de llamar a esta función.
	 * @param source
	 * @param target
	 * @param numberOfCard
	 */
	public void move(List<Card> source, List<Card> target, Integer numberOfCard){
		for (int i = 0; i < numberOfCard; i++) {
			int random = randomBetween(0, numberOfCard);
			Card removed = source.remove(random);
			target.add(removed);
		}
	}

	private Integer randomBetween(Integer min, Integer max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public void drawFromDeckToHand(Player player, Integer numberOfCard){
		move(player.getDeck(), player.getHand(), numberOfCard);
		playerService.savePlayer(player);
	}

	public void drawFromHandToPlayed(Player player){
		move(player.getHand(), player.getPlayed(), 1);
		playerService.savePlayer(player);
	}

	public void drawFromPlayedToDiscardPile(Player player){
		List<Card> hand = player.getHand();
		move(hand, player.getDiscardPile(), hand.size());
		playerService.savePlayer(player);
	}
}