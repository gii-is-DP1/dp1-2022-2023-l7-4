package org.springframework.samples.tyrantsOfTheUnderdark.cardMovements;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.MarketPlayerMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class MarketPlayerMoveCardsServiceTest {

    @Autowired
    protected GameService gameService;

    @Autowired
    protected PlayerService playerService;
    
    @Autowired
    protected MarketPlayerMoveCardsService marketPlayerMoveCardsService;

    @Autowired
    protected CardService cardService;

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
