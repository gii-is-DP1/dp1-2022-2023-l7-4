package org.springframework.samples.tyrantsOfTheUnderdark.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Autowired
    UserService userService;




    @Test
    public void testDeleteDeletableUser(){
        User deletableUser=this.userService.getUserByUsername("javfercas3");
        assertThat(deletableUser.canBeDeleted()).isTrue();
        try{
            this.userService.deleteUser(deletableUser);
        }catch(Exception e){
            fail("No deberia salir ningún error");
        }
        List<User> allUser=this.userService.getUsersList();
        assertThat(allUser.contains(deletableUser)).isFalse();
    }

    @Test
    public void testDeleteNotDeletableUser(){
        User notDeletableUser=this.userService.getUserByUsername("anddomrui");
        assertThat(notDeletableUser.canBeDeleted()).isFalse();
        assertThrows(Exception.class, ()->this.userService.deleteUser(notDeletableUser));
    }


    
}
