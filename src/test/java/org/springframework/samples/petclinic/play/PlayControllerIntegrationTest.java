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
import org.springframework.samples.petclinic.cardsMovement.MarketPlayerMoveCardsService;
import org.springframework.samples.petclinic.game.EndTurnService;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.initializer.InitializeMapService;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.samples.petclinic.initializer.IntializeGame;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PlayController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayControllerIntegrationTest {

    private static final Integer TEST_GAME_ID=3;

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PlayerUsePositionService playerUsePositionService;


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

    @MockBean
    private EndTurnService endTurnService;

    @MockBean
    private MarketPlayerMoveCardsService playerMoveCardsService;

    @MockBean
    private PlayerService playerService;

    @WithMockUser(value="spring")
    @Test
    public void testNextTurn() throws Exception{
        Game game= this.gameService.getGameById(TEST_GAME_ID);
        Player actualPlayer=game.getCurrentPlayer();
        Integer indexOfPlayer=game.getPlayers().indexOf(actualPlayer);
        mockMvc.perform(get("/play/{gameId}/round/"+game.getRound()+"/next",TEST_GAME_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/roundN"))
        .andExpect(model().attribute("player", is(game.getPlayers().get(indexOfPlayer+1))));
    }
    
}
