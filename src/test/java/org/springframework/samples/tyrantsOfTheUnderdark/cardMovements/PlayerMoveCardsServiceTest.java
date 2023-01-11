package org.springframework.samples.tyrantsOfTheUnderdark.cardMovements;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.HalfDeck;
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.PlayerMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class PlayerMoveCardsServiceTest {
    
    @Autowired
    protected PlayerMoveCardsService playerMoveCardsService;

    @Autowired
    protected PlayerService playerService;

    @Autowired
    protected CardService cardService;

    @Autowired
    protected GameService gameService;

    @Test
    public void testPlayCardPlayer() {
        Game game = gameService.getGameById(1);
        List<Card> hand = cardService.getAllCards().subList(3, 8);
        game.getCurrentPlayer().setHand(hand);
        Player player = game.getCurrentPlayer();
        Card card = player.getHand().get(4);
        playerMoveCardsService.moveFromHandToPlayed(card, player);
        assertThat(player.getHand().size()).isEqualTo(4);
        assertThat(player.getPlayed().get(0).getName()).isEqualTo("Adalid");
    }
}
