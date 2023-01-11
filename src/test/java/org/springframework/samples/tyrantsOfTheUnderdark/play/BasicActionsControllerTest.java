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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionServiceRepo;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.City;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.configuration.SecurityConfiguration;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameFormatter;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PlayController.class,
includeFilters = @ComponentScan.Filter(value = {GameFormatter.class}, type = FilterType.ASSIGNABLE_TYPE),
 excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
  excludeAutoConfiguration = SecurityConfiguration.class)
public class BasicActionsControllerTest {
    
    private static final Integer TEST_GAME_ID=100;

    private static final Integer TEST_ROUND=1;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionServiceRepo positionServiceRepo;

    @MockBean
    private GameService gameService;

    private Game game;

    @BeforeEach
    void setup(){
        game=new Game();
        game.setId(TEST_GAME_ID);
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
    
    @WithMockUser(value="spring")
    @Test
    public void testShowPlaceTroopForm() throws Exception {
        mockMvc.perform(get("{gameId}/round/{round}/basicPlaceTroop",TEST_GAME_ID,TEST_ROUND))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/chooseOnePositionFormView"))
        .andExpect(model().attribute("player", is(game.getPlayers().get(0))));
    }

    @WithMockUser(value="spring")
    @Test
    public void testProcessPlaceTroopForm() throws Exception{
        mockMvc.perform(post("{gameId}/round/{round}/basicPlaceTroop",TEST_GAME_ID,TEST_ROUND)
        .with(csrf()).param("positionId", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/"+TEST_GAME_ID+"/round/"+TEST_ROUND));        
    }

    @WithMockUser(value="spring")
    @Test
    public void testShowReturnPiece() throws Exception {
        mockMvc.perform(get("{gameId}/round/{round}/basicReturnEnemySpy",TEST_GAME_ID,TEST_ROUND))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/chooseOnePositionFormView"))
        .andExpect(model().attribute("player", is(game.getPlayers().get(0))));
    }

    @WithMockUser(value="spring")
    @Test
    public void testProcessReturnPiece() throws Exception{
        mockMvc.perform(post("{gameId}/round/{round}/basicReturnEnemySpy",TEST_GAME_ID,TEST_ROUND)
        .with(csrf()).param("positionId", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/"+TEST_GAME_ID+"/round/"+TEST_ROUND));        
    }

    @WithMockUser(value="spring")
    @Test
    public void testShowKillTroopForm() throws Exception {
        mockMvc.perform(get("{gameId}/round/{round}/basicKillTroop",TEST_GAME_ID,TEST_ROUND))
        .andExpect(status().isOk())
        .andExpect(view().name("playing/chooseOnePositionFormView"))
        .andExpect(model().attribute("player", is(game.getPlayers().get(0))));
    }

    @WithMockUser(value="spring")
    @Test
    public void testProcessKillTroopForm() throws Exception{
        mockMvc.perform(post("{gameId}/round/{round}/basicKillTroop",TEST_GAME_ID,TEST_ROUND)
        .with(csrf()).param("positionId", "1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/"+TEST_GAME_ID+"/round/"+TEST_ROUND));        
    }
}
