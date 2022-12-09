package org.springframework.samples.petclinic.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.board.position.PlayerUsePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class PlayerUsePositionServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    
    @Mock
    private PositionRepository positionRepository;

    @Mock
    private CustomListingPositionService customListingPositionService;

    private Position emptyPosition;

    private Position positionOfPlayer1;

    private Position positionOfPlayer2;

    private Player player1;

    private Player player2;


    @BeforeEach
    public void setup(){
        emptyPosition=new Position();
        positionOfPlayer1=new Position();
        positionOfPlayer2=new Position();
        player1=new Player();
        player2=new Player();
        positionOfPlayer1.setPlayer(player1);
        positionOfPlayer2.setPlayer(player2);


    }

    @Test
    public void testOccupyCorrectTroopPositionWithOutAdjacencies(){
        emptyPosition.setForSpy(false);
        Integer numTroops=player1.getTroops();
        PlayerUsePositionService playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        try{
            playerUsePositionService.occupyTroopPosition(emptyPosition, player1, false);
        }catch(Exception e){
            fail("No deberia salir error");
        }
                    assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
                    assertThat(emptyPosition.isOccupied()).isTrue();
                    assertThat(player1.getTroops()).isEqualTo(numTroops-1);
    }



    

    
    
}
