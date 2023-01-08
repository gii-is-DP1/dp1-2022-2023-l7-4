package org.springframework.samples.tyrantsOfTheUnderdark.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.MarketPlayerMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Autowired
    protected GameService gameService;

    @Autowired
    protected PlayerService playerService;

    @Autowired
    protected MarketPlayerMoveCardsService marketPlayerMoveCardsService;

    @Autowired
    protected CardService cardService;


    @Test
    public void testFindGamesByExistingName(){
        List<Game> gamesWithName=(List<Game>)this.gameService.getGameByName("Partida mapa original");
        assertThat(gamesWithName.size()).isEqualTo(1);
    }

    @Test
    public void testCreateGame() {
        Game game = gameService.getGameById(1);
        assertThat(game.getSize()).isEqualTo(4);
        assertThat(game.getName()).isEqualTo("Partida mapa original");
        assertThat(game.getFirstHalfDeck().getName()).isEqualTo("Drow");
        assertThat(game.getSecondHalfDeck().getName()).isEqualTo("Dragons");
        assertThat(game.getMapTemplate().getId()).isEqualTo(1);
    }

    @Test
    public void testBuyInMarket() throws Exception {
        Game game = gameService.getGameById(1);
        Player player = playerService.getPlayerById(1);
        player.setInfluence(2);
        List<Card> sellzone = new ArrayList<>(cardService.getAllCards()).subList(3, 8);
        game.setSellZone(sellzone);
        Card card = game.getSellZone().get(4);
        marketPlayerMoveCardsService.buyCard(card, player);
        assertThat(player.getInfluence()).isEqualTo(0);
        assertThat(player.getDiscarded().get(player.getDiscarded().size()-1).getId()).isEqualTo(7);        
    }

    @Test
    public void testBuyCardWithoutInfluence() throws Exception {
        Game game = gameService.getGameById(1);
        Player player = playerService.getPlayerById(1);
        player.setInfluence(0);
        List<Card> sellzone = new ArrayList<>(cardService.getAllCards()).subList(3, 8);
        game.setSellZone(sellzone);
        Card card = game.getSellZone().get(4);
        assertThrows(Exception.class,() -> marketPlayerMoveCardsService.buyCard(card, player));
    }

    
}
