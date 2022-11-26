package org.springframework.samples.petclinic.cardsMovement;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardServiceRepo;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class PlayerMoveCardsService {

	@Autowired
    private PlayerService playerService;

	@Autowired
	private CardServiceRepo cardService;
	
	@Autowired
	private GameService gameService;

	//TODO Movimientos de cartas internas de market y de market a player

	/**
	 * Cuando empieza una partida los jugadores empiezan con 7 nobles y 3 soldados en su deck
	 * @param game
	 */
	public void initCardsInPlayers(Game game) {
		game.getPlayers().forEach(player -> initCardsInPlayer(game, player));
	}

	public void drawFromDeckToHand(Player player, Integer numberOfCard){
		moveAndSave(player.getDeck(), player.getHand(), player);
	}

	public void moveFromHandToPlayed(Player player){
		moveAndSave(player.getHand(), player.getPlayed(), player);
	}

	public void moveAllPlayedToDiscardPile(Player player){
		moveAndSaveAll(player.getPlayed(), player.getDiscardPile(), player);
	}

	public void promoteFromPlayed(Player player){
		moveAndSave(player.getPlayed(), player.getInnerCircle(), player);
	}

	public void promoteFromDiscardPile(Player player){
		moveAndSave(player.getDiscardPile(), player.getInnerCircle(), player);
	}

	public void discardHand(Player player){
		moveAndSaveAll(player.getHand(), player.getDiscardPile(), player);
	}

	
	//privates methods (Don't touch) -----------------------------------------------------------------------------
	private void moveAndSave(List<Card> source, List<Card> target, Player player) {
		move(source, target, 1);
		playerService.savePlayer(player);
	}

	private void moveAndSaveAll(List<Card> source, List<Card> target, Player player) {
		move(source, target, source.size());
		playerService.savePlayer(player);
	}

	/**
	 * Esta funcion elimina de la lista origen(source) y la añade a la lista destino(target).
	 * HACER SAVE de los objetos que contengan las listas después de llamar a esta función.
	 * @param source
	 * @param target
	 * @param numberOfCard
	 */
	private void move(List<Card> source, List<Card> target, Integer numberOfCard){
		if(!source.isEmpty()){
			for (int i = 0; i < numberOfCard; i++) {
				int random = randomBetween(0, numberOfCard);
				Card removed = source.remove(random);
				target.add(removed);
			}
		}
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

	private Integer randomBetween(Integer min, Integer max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}