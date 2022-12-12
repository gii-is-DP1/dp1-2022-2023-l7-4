package org.springframework.samples.petclinic.game;

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

    @Autowired
    protected GameService gameService;


    @Test
    public void testFindGamesByExistingName(){
        List<Game> gamesWithName=(List<Game>)this.gameService.getGameByName("Partida 1");
        assertThat(gamesWithName.size()).isEqualTo(1);
    }



    
}
