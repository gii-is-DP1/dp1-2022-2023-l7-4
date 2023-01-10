package org.springframework.samples.tyrantsOfTheUnderdark.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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

    

    @Autowired
    private UserService userService;


    @BeforeEach
    public void setup(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void shouldDeleteAUserThatIsNotPlayingAnyGame() throws Exception{
        User deletableUser=this.userService.getUserByUsername("javfercas3");
        mockMvc.perform(get("/users/{username}/delete",deletableUser.getUsername())).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/users/list"));
    }

    @WithMockUser(username="antoonio",authorities = {"player"})
    @Test
    public void testThatOnlyAdminsCanDeleteUsers() throws Exception{
        User deletableUser=this.userService.getUserByUsername("javfercas3");
        mockMvc.perform(get("/users/{username}/delete",deletableUser.getUsername()))
        .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testInitEditUserAsAdmin() throws Exception{
        User toEditUser=this.userService.getUserByUsername("javfercas3");
        mockMvc.perform(get("/users/{username}/edit",toEditUser.getUsername()))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("user"));
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testProcessEditUserAsAdmin() throws Exception{
        User toEditUser=this.userService.getUserByUsername("javfercas3");
        mockMvc.perform(post("/users/{username}/edit",toEditUser.getUsername())
        .with(csrf())
        .param("username","javfercas3")
        .param("password","secret1")
        .param("name","Antonio")
        .param("email","javi@gmail.com")
        .param("birthDate","2002-04-08"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/users/{username}"));
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testProcessIncorrectlyEditUserAsAdmin() throws Exception{
        User toEditUser=this.userService.getUserByUsername("javfercas3");
        mockMvc.perform(post("/users/{username}/edit",toEditUser.getUsername())
        .with(csrf())
        .param("username","javfercas3")
        .param("password","secret1")
        .param("name","")
        .param("email","javi@gmail.com")
        .param("birthDate","2002-04-08"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/currentUserDetails"));
    }
    
}
