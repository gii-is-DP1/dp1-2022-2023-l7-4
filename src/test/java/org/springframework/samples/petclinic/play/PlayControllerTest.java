package org.springframework.samples.petclinic.play;



import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityService;
import org.springframework.samples.petclinic.board.sector.city.CityTemplate;
import org.springframework.samples.petclinic.board.sector.path.PathService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.initializer.InitializeMapService;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.samples.petclinic.initializer.IntializeGame;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PlayController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayControllerTest {

    private static final Integer TEST_GAME_ID=100;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerUsePositionService playerUsePositionService;

    @Autowired
    private PlayController playController;

    @MockBean
    private GameService gameService;

    @MockBean
    private PositionInGameService positionInGameService;
    
    @MockBean
    InitializeMapService initializerService;
    
    @MockBean
    InitializePositionService positionInitialiter;

    @MockBean
    private PositionServiceRepo positionServiceRepo;

    @MockBean
    private CityService cityService;

    @MockBean
    private PathService pathService;

    @MockBean
    IntializeGame gameInitializer;

    private Game game;

    @BeforeEach
    void setup(){
        game=new Game();
        game.setId(TEST_GAME_ID);
        game.setRound(0);
        game.setTurnPlayer(1);
        game.setFinished(false);
        CityTemplate cityTemplate1=new CityTemplate();
        cityTemplate1.setId(1);
        cityTemplate1.setStartingCity(true);
        City city1= City.of(cityTemplate1,game);
        Position initialTroopPosition=new Position();
        initialTroopPosition.setId(1);
        initialTroopPosition.setForSpy(false);
        List<Position> positions=new ArrayList<>();
        positions.add(initialTroopPosition);
        city1.setPositions(positions);
        Player player1=new Player();
        player1.setId(1);
        Player player2=new Player();
        player2.setId(2);
        game.setPlayers(List.of(player1,player2));
        given(this.gameService.getGameById(TEST_GAME_ID)).willReturn(game);

    }

    @WithMockUser(value="spring")
    @Test
    public void testShowRoundZero() throws Exception{
        mockMvc.perform(get("/play/{gameId}/round/0",TEST_GAME_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/roundZero"))
        .andExpect(model().attribute("player", is(game.getPlayers().get(0))));
    }

    @WithMockUser(value="spring")
    @Test
    public void testProcessRoundZero() throws Exception{
        mockMvc.perform(post("/play/{gameId}/round/0",TEST_GAME_ID)
        .with(csrf()).param("idposition", "1"))
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("playing/roundZero"));        
    }

    
}
