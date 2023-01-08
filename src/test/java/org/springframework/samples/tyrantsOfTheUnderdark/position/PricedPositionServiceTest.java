package org.springframework.samples.tyrantsOfTheUnderdark.position;


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
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.CustomListingPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PlayerUsePositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionRepository;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PricedPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class PricedPositionServiceTest {

    @Autowired
    protected PricedPositionService pricedPositionService;

    @Mock
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
    @Test
    public void testOccupyTroopWithPowerCorrectly(){
        emptyPosition.setForSpy(false);
        emptyPosition.setPlayer(null);
        positionOfPlayer1.setAdjacents(new ArrayList<>(List.of(emptyPosition)));
        player1.setPower(7);
        player1.setTroops(3);
        Integer numTroops=player1.getTroops();
        Integer currentPower=player1.getPower();
        emptyPosition.setAdjacents(new ArrayList<>(List.of(positionOfPlayer1)));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player1.getId(), false)).thenReturn(new ArrayList<>(List.of(emptyPosition)));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        pricedPositionService=
        new PricedPositionService(playerUsePositionService,playerRepository);
                try {
                    pricedPositionService.placeTroopWithPrice(player1, emptyPosition, false);;
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(emptyPosition.isOccupied()).isTrue();
        assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
        assertThat(player1.getTroops()).isEqualTo(numTroops-1);
        assertThat(player1.getPower()).isEqualTo(currentPower-1);
        }

        @Test
    public void testOccupyTroopWithPowerInSpecialCaseCorrectly(){
        emptyPosition.setForSpy(false);
        emptyPosition.setPlayer(null);
        emptyPosition.setAdjacents(List.of());
        positionOfPlayer1.setAdjacents(List.of());
        player1.setPower(7);
        player1.setTroops(3);
        Integer numTroops=player1.getTroops();
        Integer currentPower=player1.getPower();
        assertThat(!emptyPosition.getAdjacents().contains(positionOfPlayer1)).isTrue();
                try {
                    pricedPositionService.placeTroopWithPrice(player1, emptyPosition, true);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(emptyPosition.isOccupied()).isTrue();
        assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
        assertThat(player1.getTroops()).isEqualTo(numTroops-1);
        assertThat(player1.getPower()).isEqualTo(currentPower-1);
        }

        @Test
        public void testOccupyTroopWithoutEnoughPower(){
            emptyPosition.setForSpy(false);
            emptyPosition.setPlayer(null);
            player1.setPower(0);
            assertThrows(Exception.class,()->pricedPositionService.placeTroopWithPrice(player1, emptyPosition, true));
            }


        @Test
    public void testKillEnemyTroopWithPowerCorrectly(){
        positionOfPlayer2.setForSpy(false);
        positionOfPlayer1.setAdjacents(new ArrayList<>(List.of(positionOfPlayer2)));
        player1.setPower(7);
        player1.setTroops(3);
        player1.setTrophyHall(new ArrayList<>());
        Integer trophyHall=player1.getTrophyHallVPs();
        Integer currentPower=player1.getPower();
        positionOfPlayer2.setAdjacents(new ArrayList<>(List.of(positionOfPlayer1)));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player1.getId(), true)).thenReturn(new ArrayList<>(List.of(positionOfPlayer2)));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        pricedPositionService=
        new PricedPositionService(playerUsePositionService,playerRepository);
                try {
                    pricedPositionService.killEnemyTroopWithPrice(player1, positionOfPlayer2);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(positionOfPlayer2.isOccupied()).isFalse();
        assertThat(player1.getTrophyHallVPs()).isEqualTo(trophyHall+1);
        assertThat(player1.getPower()).isEqualTo(currentPower-3);
        }

        @Test
        public void testKillEnemyTroopWithoutEnoughPower(){
            player1.setPower(0);
            assertThrows(Exception.class,()->pricedPositionService.killEnemyTroopWithPrice(player1, positionOfPlayer2));
         }

         @Test
    public void testReturnEnemySpyWithPowerCorrectly(){
        positionOfPlayer2.setForSpy(true);
        positionOfPlayer1.setAdjacents(new ArrayList<>(List.of(positionOfPlayer2)));
        player1.setPower(7);
        player2.setSpies(3);
        Integer numerOfSpyOfPlayer2=player2.getSpies();
        Integer currentPower=player1.getPower();
        positionOfPlayer2.setAdjacents(new ArrayList<>(List.of(positionOfPlayer1)));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player1.getId(), true)).thenReturn(new ArrayList<>(List.of(positionOfPlayer2)));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        pricedPositionService=
        new PricedPositionService(playerUsePositionService,playerRepository);
                try {
                    pricedPositionService.returnEnemySpyWithPrice(player1, positionOfPlayer2);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(positionOfPlayer2.isOccupied()).isFalse();
        assertThat(player2.getSpies()).isEqualTo(numerOfSpyOfPlayer2+1);
        assertThat(player1.getPower()).isEqualTo(currentPower-3);
        }

        @Test
        public void testReturnEnemySpyWithoutEnoughPower(){
            player1.setPower(0);
            assertThrows(Exception.class,()->pricedPositionService.returnEnemySpyWithPrice(player1, positionOfPlayer2));
         }
    }
    

    
    

