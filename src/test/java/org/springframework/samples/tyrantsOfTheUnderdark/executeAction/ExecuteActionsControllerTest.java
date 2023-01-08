package org.springframework.samples.tyrantsOfTheUnderdark.executeAction;

import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.tyrantsOfTheUnderdark.card.Card;
import org.springframework.samples.tyrantsOfTheUnderdark.card.CardService;
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

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Autowired
    private CardService cardService;

    @Autowired
    private GameService gameService;

    



    @BeforeEach
    public void setup(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }
    /*
     * @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testExecuteDevoreByCard() throws Exception{
        Card toDevore=Card.createCardToDevore();
        toDevore.setId(100);
        toDevore.setHalfDeck(this.cardService.getHalfDeckById(1));
        Game game=this.gameService.getGameById(1);
        game.setRound(1);
        mockMvc.perform(get("/play/{gameId}/round/1/play-card/{cardId}",1,toDevore.getId()))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/play/1/round/1/devoreMarketCard"));
    }
     */

    
}
