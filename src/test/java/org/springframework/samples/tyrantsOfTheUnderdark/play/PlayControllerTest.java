package org.springframework.samples.tyrantsOfTheUnderdark.play;




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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
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
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.InitializeMapService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.InitializePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.initializer.IntializeGame;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.mypackage"})
@SpringBootTest
@AutoConfigureMockMvc
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
        player1.setGame(game);
        Player player2=new Player();
        player2.setId(2);
        player2.setInfluence(1);
        player2.setGame(game);
        game.setCities(new ArrayList<>(List.of(city1)));
        game.setPlayers(List.of(player1,player2));
        given(this.gameService.getGameById(TEST_GAME_ID)).willReturn(game);
        given(this.positionServiceRepo.getAllPositionsByGame(game)).willReturn(List.of(new Position()));


    }

    @WithMockUser(username = "daviddhc", password = "player1", authorities = {"player"})
    @Test
    public void testShowRoundZero() throws Exception{ //HISTORIAS DE USUARIOS MENCIONADAS: H30
        mockMvc.perform(get("/play/{gameId}/round/0",TEST_GAME_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/roundZero"))
        .andExpect(model().attribute("player", is(game.getPlayers().get(0))));
    }

    @WithMockUser(username = "daviddhc", password = "player1", authorities = {"player"})
    @Test
    public void testProcessRoundZero() throws Exception{ //HISTORIAS DE USUARIOS MENCIONADAS: H30
        mockMvc.perform(post("/play/{gameId}/round/0",TEST_GAME_ID)
        .with(csrf()).param("positionId", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/"+TEST_GAME_ID));        
    }

    @WithMockUser(username = "daviddhc", password = "player1", authorities = {"player"})
    @Test
    public void testShowRoundN() throws Exception{ // HISTORIAS DE USUARIOS MENCIONADAS: H28,H29,H31
        game.setRound(1);
        Player actualPlayer=game.getCurrentPlayer();
        mockMvc.perform(get("/play/{gameId}/round/"+game.getRound(),TEST_GAME_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/roundN"))
        .andExpect(model().attribute("player", is(actualPlayer)))
        .andExpect(model().attribute("turn", is(game.getTurnPlayer())))
        .andExpect(model().attribute("totalinnerCirclevp",is( game.getInnerCircleVP(actualPlayer))))
        .andExpect(model().attribute("controlMarkers",is(game.getActualControlMarker())))
        .andExpect(model().attribute("totalControlMarkers",is(game.getActualTotalControlMarker())))
        .andExpect(model().attribute("positions", is(this.positionServiceRepo.getAllPositionsByGame(game))));
    }

    @WithMockUser(username = "daviddhc", password = "player1", authorities = {"player"})
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