package org.springframework.samples.petclinic.initializer;

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
public class initializerService {

	@Autowired
    private PlayerService playerService;

	@Autowired
	private CardServiceRepo cardService;
	
	@Autowired
	private GameService gameService;
    
    
    /**
	 * Cuando empieza una partida los jugadores empiezan con 7 nobles y 3 soldados en su deck
	 * @param game
	 */
	public void initCardsInPlayers(Game game) {
		game.getPlayers().forEach(player -> initCardsInPlayer(game, player));
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
}
