package org.springframework.samples.petclinic.position;


import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class CustomListingPositionServiceTest {

    @Autowired
    protected CustomListingPositionService customListingPositionService;

    @Mock
    private PositionServiceRepo positionServiceRepo;


    private Player player1;

    private Game game;

    private Player player2;

    @BeforeEach
    void setup(){
        this.player1=new Player();
        this.player2=new Player();
        this.game=new Game();
        City city1=new City();
        City city2=new City();
        Position spyFromCity1=new Position();
        //List<Position> spyPlayerPositions=new ArrayList<>(List.of(position))
    }

    @Test
    public void testGetFreeSpyPositionsFromPlayer(){

        
    }



    
}
