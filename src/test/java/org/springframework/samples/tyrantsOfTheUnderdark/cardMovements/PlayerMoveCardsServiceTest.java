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
        List<Card> hand = cardService.getAllCards().subList(0, 5);
        game.getCurrentPlayer().setHand(hand);
        Player player = game.getCurrentPlayer();
        Card card = player.getHand().get(2);
        playerMoveCardsService.moveFromHandToPlayed(card, player);
        assertThat(player.getHand().size()).isEqualTo(4);
        assertThat(player.getPlayed().get(0).getName()).isEqualTo("Noble");
    }

    @Test
    public void testPromoteCardFromPlayed(){
        Game game = gameService.getGameById(1);
        List<Card> hand = cardService.getAllCards().subList(0, 5);
        game.getCurrentPlayer().setPlayed(hand);
        Player player = game.getCurrentPlayer();
        Card card = player.getPlayed().get(2);
        assertThat(player.getInnerCircle().contains(card)).isFalse();
        playerMoveCardsService.promoteSelectedFromPlayed(card, player);
        assertThat(player.getPlayed().contains(card)).isFalse();
        assertThat(player.getInnerCircle().contains(card)).isTrue();
    }

    @Test
    public void testPromoteCardFromDiscardPile(){
        Game game = gameService.getGameById(1);
        List<Card> hand = cardService.getAllCards().subList(0, 5);
        game.getCurrentPlayer().setDiscarded(hand);
        Player player = game.getCurrentPlayer();
        Card card = player.getDiscarded().get(2);
        assertThat(player.getInnerCircle().contains(card)).isFalse();
        playerMoveCardsService.promoteSelectedFromDiscardPile(card, player);
        assertThat(player.getDiscarded().contains(card)).isFalse();
        assertThat(player.getInnerCircle().contains(card)).isTrue();
    }

    @Test
    public void testPromoteCardFromDeck(){
        Game game = gameService.getGameById(1);
        List<Card> hand = cardService.getAllCards().subList(0, 5);
        game.getCurrentPlayer().setDeck(hand);
        Player player = game.getCurrentPlayer();
        Card card = player.getDeck().get(2);
        assertThat(player.getInnerCircle().contains(card)).isFalse();
        playerMoveCardsService.promoteSelectedFromDeck(card, player);
        assertThat(player.getDeck().contains(card)).isFalse();
        assertThat(player.getInnerCircle().contains(card)).isTrue();
    }
}
