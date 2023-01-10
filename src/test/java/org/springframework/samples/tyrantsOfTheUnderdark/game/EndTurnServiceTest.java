package org.springframework.samples.tyrantsOfTheUnderdark.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class EndTurnServiceTest {
    
    @Autowired
    protected EndTurnService endTurnService;

    @Autowired
    protected GameService gameService;

    @Test
    public void testEndTurn() {
        Game game = gameService.getGameById(1);
        Player playerTurn1 = game.getCurrentPlayer();
        endTurnService.execute(game);
        Player playerTurn2 = game.getCurrentPlayer();
        assertThat(playerTurn1).isNotEqualTo(playerTurn2);
    }
}
