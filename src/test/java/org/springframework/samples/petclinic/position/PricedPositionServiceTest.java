package org.springframework.samples.petclinic.position;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.samples.petclinic.board.position.PricedPositionService;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class PricedPositionServiceTest {

    @Autowired
    protected PricedPositionService pricedPositionService;

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
        this.player1.setId(1);
        this.player2=new Player();
        this.player2.setId(2);
        this.positionOfPlayer1.setPlayer(player1);
        this.positionOfPlayer2.setPlayer(player2);
    }

    //PENDIENTE DE REALIZACIÓN
    /*
     * @Test
    public void testOccupyTroopWithPower(){
        emptyPosition.setForSpy(false);
        positionOfPlayer1.setAdjacents(new ArrayList<>(List.of(emptyPosition)));
        player1.setPower(7);
        Integer numTroops=player1.getTroops();
        Integer currentPower=player1.getPower();
        emptyPosition.setAdjacents(new ArrayList<>(List.of(positionOfPlayer1)));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player1.getId(), false)).thenReturn(new ArrayList<>(List.of(emptyPosition)));
        pricedPositionService=
        new PricedPositionService(playerUsePositionService,playerRepository);
                try {
                    pricedPositionService.placeTroopWithPrice(player1 ,emptyPosition);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
        assertThat(emptyPosition.isOccupied()).isTrue();
        assertThat(player1.getTroops()).isEqualTo(numTroops-1);
        assertThat(player1.getPower()).isEqualTo(currentPower-1);
        }
     */
    }
    

