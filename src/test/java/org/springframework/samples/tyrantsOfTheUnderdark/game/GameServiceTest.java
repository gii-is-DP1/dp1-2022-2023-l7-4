package org.springframework.samples.tyrantsOfTheUnderdark.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    private final Integer NUMBER_OF_GAMES_WITH_ORIGINAL_MAP=4;

    @Autowired
    protected GameService gameService;

    @Test
    public void testFindGamesByExistingName(){
        List<Game> gamesWithName=(List<Game>)this.gameService.getGameByName("Partida mapa original");
        assertThat(gamesWithName.size()).isEqualTo(NUMBER_OF_GAMES_WITH_ORIGINAL_MAP);
    }
    @Test
    public void testFindAllGames(){
        List<Game> gamesWithName=(List<Game>)this.gameService.getGameByName("");
        assertThat(gamesWithName.size()).isEqualTo(this.gameService.getGames().size());
    }

    @Test
    public void testShouldntFindAnyGameWithInexistentName(){
        List<Game> gamesWithName=(List<Game>)this.gameService.getGameByName("mapa que no existe");
        assertThat(gamesWithName.size()).isEqualTo(0);
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
   
}
