package org.springframework.samples.tyrantsOfTheUnderdark.game;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.board.map.MapTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.card.HalfDeck;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Autowired
    protected GameService gameService;


    @Test
    public void testFindGamesByExistingName(){
        List<Game> gamesWithName=(List<Game>)this.gameService.getGameByName("Partida 1");
        assertThat(gamesWithName.size()).isEqualTo(1);
    }

    @Test
    public void testCreateGame() {
        Game game = new Game();
        game.setName("Partida Nueva");
        gameService.addPlayerByUsername(game, "anddomrui");
        gameService.addPlayerByUsername(game, "daviddhc");
        MapTemplate mapTemplate = new MapTemplate();
        mapTemplate.setId(1);
        game.setMapTemplate(mapTemplate);
        HalfDeck firstHalfDeck = new HalfDeck();
        firstHalfDeck.setName("Dragons");
        HalfDeck secondHalfDeck = new HalfDeck();
        secondHalfDeck.setName("Drow");
        game.setFirstHalfDeck(firstHalfDeck);
        game.setSecondHalfDeck(secondHalfDeck);
        assertThat(game.getSize()).isEqualTo(2);
        assertThat(game.getName()).isEqualTo("Partida Nueva");
        assertThat(game.getFirstHalfDeck().getName()).isEqualTo("Dragons");
        assertThat(game.getSecondHalfDeck().getName()).isEqualTo("Drow");
        assertThat(game.getMapTemplate().getId()).isEqualTo(1);
    }

    
}
