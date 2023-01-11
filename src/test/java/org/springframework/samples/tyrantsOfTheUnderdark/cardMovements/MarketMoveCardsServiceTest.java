package org.springframework.samples.tyrantsOfTheUnderdark.cardMovements;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.MarketMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class MarketMoveCardsServiceTest {

    @Autowired
    MarketMoveCardsService marketMoveCardsService;

    @Autowired
	private GameService gameService;

    @Autowired
    private CardService cardService;


    @Test
    public void testDevoreCardInSellZone(){
        Game game = gameService.getGameById(1);
        List<Card> sellzone = new ArrayList<>(cardService.getAllCards()).subList(3, 8);
        game.setSellZone(sellzone);
        Card card = game.getSellZone().get(4);
        marketMoveCardsService.devourCardFromSellZone(card, game);
        assertThat(game.getDevoured()).contains(card);
        assertThat(!game.getSellZone().contains(card)).isTrue();
    }
    
}
