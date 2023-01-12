package org.springframework.samples.tyrantsOfTheUnderdark.game;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class GameControllerTest {

    private final Integer TEST_GAME_ID=1;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;


    @BeforeEach
    public void setup(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

     @WithMockUser(username="anddomrui",authorities = {"player"})
    @Test
    public void testShowMyGames() throws Exception{
        mockMvc.perform(get("/games/list"))
        .andExpect(status().isOk())
        .andExpect(view().name("games/gameList"));
    }

    @WithMockUser(username="anddomrui",authorities = {"player"})
    @Test
    public void testShowGameDetails() throws Exception{
        mockMvc.perform(get("/games/{gameId}",TEST_GAME_ID))
        .andExpect(status().isOk())
        .andExpect(view().name("games/gameDetails"));
    }
     
    
}
