package org.springframework.samples.petclinic.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    protected PlayerUsePositionService playerUsePositionService;

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
        this.emptyPosition=new Position();
        this.positionOfPlayer1=new Position();
        this.positionOfPlayer2=new Position();
        this.player1=new Player();
        this.player2=new Player();
        this.positionOfPlayer1.setPlayer(player1);
        this.positionOfPlayer2.setPlayer(player2);
    }

    @Test
    public void testOccupyCorrectTroopPositionWithOutAdjacencies(){
        emptyPosition.setForSpy(false);
        Integer numTroops=player1.getTroops();

        try{
            playerUsePositionService.occupyTroopPosition(emptyPosition, player1, false);
        }catch(Exception e){
            fail("No deberia salir error");
        }
                    assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
                    assertThat(emptyPosition.isOccupied()).isTrue();
                    assertThat(player1.getTroops()).isEqualTo(numTroops-1);
    }

    @Test
    public void testOccupyCorrectTroopPositionWithAdjacencies(){
        Position adjPosToEmptyPos=new Position();
        emptyPosition.setForSpy(false);
        adjPosToEmptyPos.setAdjacents(new ArrayList<>(List.of(emptyPosition)));
        positionOfPlayer1.setAdjacents(new ArrayList<>(List.of(emptyPosition)));
        Integer numTroops=player1.getTroops();
        emptyPosition.setAdjacents(new ArrayList<>(List.of(positionOfPlayer1,adjPosToEmptyPos)));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player1.getId(), false)).thenReturn(new ArrayList<>(List.of(emptyPosition)));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        //assertThat(customListingPositionService.getPresencePositions(player1.getId(), false)).contains(emptyPosition);
        //assertThat(customListingPositionService.getPresenceTroopPositions(player1.getId(), false)).doesNotContain(adjPosToEmptyPos);
                try {
                    playerUsePositionService.occupyTroopPosition(emptyPosition, player1, true);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
        assertThat(emptyPosition.isOccupied()).isTrue();
        assertThat(player1.getTroops()).isEqualTo(numTroops-1);
        }
}



    

    
    

