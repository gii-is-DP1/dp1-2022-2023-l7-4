package org.springframework.samples.tyrantsOfTheUnderdark.play;



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
import org.springframework.samples.tyrantsOfTheUnderdark.configuration.SecurityConfiguration;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PlayerUsePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionServiceRepo;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.City;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.PathService;
import org.springframework.samples.tyrantsOfTheUnderdark.cardsMovement.MarketPlayerMoveCardsService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.EndTurnService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;

import org.springframework.samples.tyrantsOfTheUnderdark.game.GameFormatter;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.InitializeMapService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.InitializePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.IntializeGame;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PlayController.class,
includeFilters = @ComponentScan.Filter(value = {GameFormatter.class}, type = FilterType.ASSIGNABLE_TYPE),
 excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
  excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayControllerTest {

    private static final Integer TEST_GAME_ID=100;

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



    private Game game;



    @BeforeEach
    void setup(){
        game=new Game();
        game.setId(TEST_GAME_ID);
        game.setRound(0);
        game.setTurnPlayer(1);
        game.setFinished(false);
        game.setLolths(List.of());
        game.setHouseGuards(List.of());
        game.setHouseGuards(List.of());
        
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
        player1.setInfluence(3);
        Player player2=new Player();
        player2.setId(2);
        player2.setInfluence(1);
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
        .with(csrf()).param("positionId", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/"+TEST_GAME_ID));        
    }

    @WithMockUser(value="spring")
    @Test
    public void testShowRoundN() throws Exception{
        game.setRound(1);
        Player actualPlayer=game.getCurrentPlayer();
        mockMvc.perform(get("/play/{gameId}/round/"+game.getRound(),TEST_GAME_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/roundN"))
        .andExpect(model().attribute("player", is(actualPlayer)))
        .andExpect(model().attribute("turn", is(game.getTurnPlayer())))
        .andExpect(model().attribute("totalinnerCirclevp",is( game.getInnerCircleVP(actualPlayer))));
    }

    @WithMockUser(value="spring")
    @Test
    public void testNextTurn() throws Exception{
        Game game= this.gameService.getGameById(TEST_GAME_ID);
        game.setTurnPlayer(1);
        game.setRound(1);
        mockMvc.perform(get("/play/{gameId}/round/"+game.getRound()+"/next",TEST_GAME_ID))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/{gameId}"));
    }








    
}