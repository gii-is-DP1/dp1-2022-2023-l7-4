package org.springframework.samples.tyrantsOfTheUnderdark.executeAction;

import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
import org.springframework.samples.tyrantsOfTheUnderdark.card.action.Action;
import org.springframework.samples.tyrantsOfTheUnderdark.card.action.ActionService;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ExecuteActionsControllerTest {

    private final Integer CARD_DEVOUR_ID=37;

    private final Integer CARD_END_TURN_ID=7;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;


    @Autowired
    private GameService gameService;

    @Autowired
    private ActionService actionService;

    @Autowired
    private CardService cardService;


    //PASOS PARA OBTENER TEST E2E FUNCIONALES DE EXECUTEACTIONCONTROLLER:
    //1-CREAR ACCIÓN DE PRUEBA CON LA ACCIÓN QUE QUIERES
    //2-ASIGNARLE UNA CARTA
    //3- GUARDAR ACCIÓN , AÑADIRSELA A GAME Y GUARDAR GAME



    @BeforeEach
    public void setup(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }
     @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testExecuteDevoreByCard() throws Exception{
        Action actionToDevore=Action.ofDevoreCard();
        actionToDevore.setCard(this.cardService.getCardById(CARD_DEVOUR_ID));
        this.actionService.save(actionToDevore);
        Game game=this.gameService.getGameById(1);
        game.setRound(1);
        game.setCurrentAction(actionToDevore);
        this.gameService.save(game);
        mockMvc.perform(get("/play/{gameId}/round/1/execute-action",1))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/{gameId}/round/{round}/devoreMarketCard"));
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testExecuteActionEndOfTurn() throws Exception{
        Action endOfTurnAction=Action.ofEndOfTurn();
        endOfTurnAction.setCard(this.cardService.getCardById(CARD_END_TURN_ID));
        this.actionService.save(endOfTurnAction);
        Game game=this.gameService.getGameById(1);
        game.setRound(1);
        game.setEndTurnAction(endOfTurnAction);
        this.gameService.save(game);
        mockMvc.perform(get("/play/{gameId}/round/1/next",1))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/{gameId}/round/{round}/execute-action"));
    }
     

    
}
