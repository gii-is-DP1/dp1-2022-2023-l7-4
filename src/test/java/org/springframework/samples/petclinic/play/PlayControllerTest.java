package org.springframework.samples.petclinic.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.initializer.InitializeMapService;
import org.springframework.samples.petclinic.initializer.InitializePositionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

@WebMvcTest(controllers = PlayController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayControllerTest {

    @Autowired
    private MockBean mockMvc;

    @MockBean
    private PlayerUsePositionService playerUsePositionService;

    @Autowired
    InitializeMapService initializerService;

    @Autowired
    InitializePositionService positionInitialiter;
    
    @Autowired
    private PositionServiceRepo positionServiceRepo;
}
