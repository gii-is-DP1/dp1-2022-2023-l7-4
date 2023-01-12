package org.springframework.samples.tyrantsOfTheUnderdark.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    private final String TEST_USERNAME="javfercas3";

    private final String TEST_NOT_DELETABLE_USERNAME="anddomrui";

    @BeforeEach
    public void setup(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @WithMockUser(username="admin1",authorities = {"admin"})
    @Test
    public void shouldDeleteAUserThatIsNotPlayingAnyGame() throws Exception{
        mockMvc.perform(get("/users/{username}/delete",TEST_USERNAME)).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/users/list"));
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void shouldSeeListOfUsers() throws Exception{
        mockMvc.perform(get("/users/list"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/users/pagination?name=&page=1"));
    }

    @WithMockUser(username="antoniojose",authorities = {"player"})
    @Test
    public void shouldntSeeListOfUsers() throws Exception{
        mockMvc.perform(get("/users/list"))
        .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser(username="antoonio",authorities = {"player"})
    @Test
    public void testThatOnlyAdminsCanDeleteUsers() throws Exception{
        mockMvc.perform(get("/users/{username}/delete",TEST_USERNAME))
        .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testInitEditUserAsAdmin() throws Exception{
        mockMvc.perform(get("/users/{username}/edit",TEST_USERNAME))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("user"));
    }

    @WithMockUser(username=TEST_USERNAME,authorities = {"player"})
    @Test
    public void testDeleteMyProfileCorrectly() throws Exception{
        mockMvc.perform(get("/deleteprofile"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/logout"));
    }

    @WithMockUser(username=TEST_NOT_DELETABLE_USERNAME,authorities = {"player"})
    @Test
    public void testDeleteMyProfileWithNotFinishedGames() throws Exception{
        mockMvc.perform(get("/deleteprofile"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
    }

    
}
