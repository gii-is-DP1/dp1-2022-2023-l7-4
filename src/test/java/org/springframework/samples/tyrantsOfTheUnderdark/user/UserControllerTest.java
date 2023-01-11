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

    private final String TEST_USERNAME="javfercas3";

    



    @BeforeEach
    public void setup(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @WithMockUser(username="admin2",authorities = {"admin"})
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

    @WithMockUser(username="admin2",authorities = {"admin"})
    @Test
    public void testProcessEditUserAsAdmin() throws Exception{
        mockMvc.perform(post("/users/{username}/edit",TEST_USERNAME)
        .with(csrf())
        .param("username","javfercas3")
        .param("password","secret1")
        .param("name","Antonio")
        .param("email","javi@gmail.com")
        .param("birthDate","2002-04-08"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/currentUserDetails"));
    }

    

    @WithMockUser(username="javfercas3",authorities = {"player"})
    @Test
    public void testInitEditMyProfile() throws Exception{
        mockMvc.perform(get("/myprofile"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("user"));
    }

    @WithMockUser(username="javfercas3",authorities = {"player"})
    @Test
    public void testProcessCorrectlyEditMyProfile() throws Exception{
        mockMvc.perform(post("/myprofile")
        .with(csrf())
        .param("username","javfercas3")
        .param("password","secret1")
        .param("name","Antonio")
        .param("email","javi@gmail.com")
        .param("birthDate","2002-04-08"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/currentUserDetails"));
    }

    @WithMockUser(username="javfercas3",authorities = {"player"})
    @Test
    public void testProcessInorrectlyEditMyProfileNotUsingNotValidName() throws Exception{
        mockMvc.perform(post("/myprofile")
        .with(csrf())
        .param("username","javfercas3")
        .param("password","secret1")
        .param("name","")
        .param("email","javi@gmail.com")
        .param("birthDate","2002-04-08"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/currentUserDetails"));
    }

    @WithMockUser(username="javfercas3",authorities = {"player"})
    @Test
    public void testProcessInorrectlyEditMyProfileNotUsingNotValidUsername() throws Exception{
        mockMvc.perform(post("/myprofile")
        .with(csrf())
        .param("username","")
        .param("password","secret1")
        .param("name","antoni3")
        .param("email","javi@gmail.com")
        .param("birthDate","2002-04-08"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/currentUserDetails"));
    }

    @WithMockUser(username="javfercas3",authorities = {"player"})
    @Test
    public void testProcessInorrectlyEditMyProfileNotUsingNotValidEmail() throws Exception{
        mockMvc.perform(post("/myprofile")
        .with(csrf())
        .param("username","javfercas3")
        .param("password","secret1")
        .param("name","antoni3")
        .param("email","")
        .param("birthDate","2002-04-08"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/currentUserDetails"));
    }

    @WithMockUser(username="javfercas3",authorities = {"player"})
    @Test
    public void testProcessInorrectlyEditMyProfileNotUsingNotValidBirthDate() throws Exception{
        mockMvc.perform(post("/myprofile")
        .with(csrf())
        .param("username","javfercas3")
        .param("password","secret1")
        .param("name","antoni3")
        .param("email","anddomrui@gmail.com")
        .param("birthDate","20"))
        .andExpect(status().isOk())
        .andExpect(view().name("users/currentUserDetails"));
    }
    
}
