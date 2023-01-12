package org.springframework.samples.tyrantsOfTheUnderdark.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class EndTurnServiceTest {
    
    @Autowired
    protected EndTurnService endTurnService;

    @Autowired
    protected GameService gameService;

    @Autowired
    protected CardService cardService;

    @Test
    public void testEndTurn() {
        Game game = gameService.getGameById(1);
        Player playerTurn1 = game.getCurrentPlayer();
        endTurnService.execute(game);
        Player playerTurn2 = game.getCurrentPlayer();
        assertThat(playerTurn1).isNotEqualTo(playerTurn2);
    }

    @Test
    public void testFinishGameWhenThereIsNoMoreBuyableCards(){
        Game game = gameService.getGameById(1);
        game.setGameDeck(new ArrayList<Card>());
        game.setTurnPlayer(game.getPlayers().size());
        gameService.save(game);
        assertThat(gameService.getGameById(1).isFinished()).isFalse();
        endTurnService.execute(game);
        assertThat(gameService.getGameById(1).isFinished()).isTrue();
    }

    @Test
    public void testFinishGameWhenOnePlayerHasNoMoreTroops(){
        Game game = gameService.getGameById(1);
        Player player=game.getCurrentPlayer();
        player.setTroops(0);
        game.setTurnPlayer(game.getPlayers().size());
        gameService.save(game);
        assertThat(gameService.getGameById(1).isFinished()).isFalse();
        endTurnService.execute(game);
        assertThat(gameService.getGameById(1).isFinished()).isTrue();
    }

    @Test
    public void testNotFinishGame(){
        Game game = gameService.getGameById(1);
        game.setGameDeck(cardService.getAllCards());
        assertThat(game.getPlayers().stream().anyMatch(player->player.getTroops()==0)).isFalse();
        endTurnService.execute(game);
        assertThat(gameService.getGameById(1).isFinished()).isFalse();

    }
}
