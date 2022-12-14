package org.springframework.samples.petclinic.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.game.Game;
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
        this.player1.setId(1);
        this.player2=new Player();
        this.player2.setId(2);
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
                try {
                    playerUsePositionService.occupyTroopPosition(emptyPosition, player1, true);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
        assertThat(emptyPosition.isOccupied()).isTrue();
        assertThat(player1.getTroops()).isEqualTo(numTroops-1);
        }

    @Test
    public void testOccupyTroopPositionAlreadyOccupy(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.occupyTroopPosition(positionOfPlayer2,player1,false));
    }
    @Test
    public void testOccupyIncorrectTypeOfPosition(){
        emptyPosition.setForSpy(true);
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.occupyTroopPosition(emptyPosition,player1,false));
    }

    @Test
    public void testOccupyTroopPositionWithoutPresence(){
        Position wantedPos=new Position();
        emptyPosition.setAdjacents(new ArrayList<>(List.of(positionOfPlayer1,wantedPos)));
        positionOfPlayer1.setAdjacents(new ArrayList<>(List.of(emptyPosition)));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.occupyTroopPosition(wantedPos,player1,true));
    }

    @Test
    public void testOccupySpyPosition(){
            player1.setSpies(5);
            Integer numSpies=player1.getSpies();
            City city=new City();
            city.setId(1);
            emptyPosition.setForSpy(true);
            emptyPosition.setCity(city);
            playerUsePositionService=
            new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
                        try {
                            playerUsePositionService.occupySpyPosition(emptyPosition, player1);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            fail("No deberia dar ninguna excepción");
                        } 
            assertThat(emptyPosition.getPlayer().getId()).isEqualTo(player1.getId());
            assertThat(emptyPosition.isOccupied()).isTrue();
            assertThat(player1.getSpies()).isEqualTo(numSpies-1);
    }

    @Test
    public void testOccupyTroopPositionWithASpy(){
        emptyPosition.setForSpy(false);
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.occupySpyPosition(emptyPosition, player1));
    }

    @Test
    public void testOccupySpyPositionInSameCity(){
        City city=new City();
        city.setId(1);
        emptyPosition.setForSpy(true);
        positionOfPlayer1.setCity(city);
        positionOfPlayer1.setForSpy(true);
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.occupySpyPosition(emptyPosition, player1));
    }

    @Test
    public void testKillAnyTroop(){
        Integer currentPV=player2.getTrophyHall().size();
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThat(positionOfPlayer1.isOccupied()).isTrue();
        try {
            playerUsePositionService.killTroop(positionOfPlayer1, player2, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(positionOfPlayer1.isOccupied()).isFalse();
        assertThat(player2.getTrophyHall().size()).isEqualTo(currentPV+1);
    }

    @Test
    public void testKillAdjacentTroop(){
        Integer currentPV=player2.getTrophyHall().size();
        positionOfPlayer2.setAdjacents(List.of(positionOfPlayer1));
        positionOfPlayer1.setAdjacents(List.of(positionOfPlayer2));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player2.getId(), true)).thenReturn(List.of(positionOfPlayer1));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThat(positionOfPlayer1.isOccupied()).isTrue();
        try {
            playerUsePositionService.killTroop(positionOfPlayer1, player2, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(positionOfPlayer1.isOccupied()).isFalse();
        assertThat(player2.getTrophyHall().size()).isEqualTo(currentPV+1);
    }

    @Test
    public void testKillEmptyPosition(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.killTroop(emptyPosition,player1,false));
    }

    @Test
    public void testKillOwnPosition(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.killTroop(positionOfPlayer1,player1,false));
    }

    @Test
    public void testKillWithoutEnoughPresence(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.killTroop(positionOfPlayer1,player2,true));
    }

    @Test
    public void testReturnPiece(){
        Integer currentTroops=player2.getTroops();
        positionOfPlayer2.setAdjacents(List.of(positionOfPlayer1));
        positionOfPlayer1.setAdjacents(List.of(positionOfPlayer2));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player1.getId(), true)).thenReturn(List.of(positionOfPlayer2));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThat(positionOfPlayer1.isOccupied()).isTrue();
        try {
            playerUsePositionService.returnPiece(positionOfPlayer2, player1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(positionOfPlayer2.isOccupied()).isFalse();
        assertThat(player2.getTroops()).isEqualTo(currentTroops+1);
    }

    @Test
    public void testReturnWithoutEnoughPresence(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.returnPiece(positionOfPlayer2,player1));
    }

    @Test
    public void testSupplantAnyTroop(){
        Integer currentPV=player2.getTrophyHall().size();
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThat(positionOfPlayer1.isOccupied()).isTrue();
        try {
            playerUsePositionService.killTroop(positionOfPlayer1, player2, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(positionOfPlayer1.isOccupied());
        assertThat(player2.getTrophyHall().size()).isEqualTo(currentPV+1);
    }

    @Test
    public void testSupplantAdjTroop(){
        Integer currentPV=player2.getTrophyHall().size();
        positionOfPlayer2.setAdjacents(List.of(positionOfPlayer1));
        positionOfPlayer1.setAdjacents(List.of(positionOfPlayer2));
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player2.getId(), true)).thenReturn(List.of(positionOfPlayer1));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThat(positionOfPlayer1.isOccupied()).isTrue();
        try {
            playerUsePositionService.supplantTroop(positionOfPlayer1, player2, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(positionOfPlayer1.isOccupied());
        assertThat(player2.getTrophyHall().size()).isEqualTo(currentPV+1);
    }

    @Test
    public void testSupplantOwnPosition(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.supplantTroop(positionOfPlayer2,player2,false));
    }

    @Test
    public void testSupplantWithoutEnoughPresence(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.supplantTroop(positionOfPlayer2,player1,true));
    }

    @Test
    public void testMovePieceCorrectly(){
        positionOfPlayer2.setAdjacents(List.of(positionOfPlayer1));
        positionOfPlayer1.setAdjacents(List.of(positionOfPlayer2));
        positionOfPlayer1.setForSpy(false);
        emptyPosition.setForSpy(false);
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player2.getId(), true)).thenReturn(List.of(positionOfPlayer1));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThat(positionOfPlayer1.isOccupied()).isTrue();
        try {
            playerUsePositionService.movePiece(positionOfPlayer1, emptyPosition, player2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(positionOfPlayer1.isOccupied()).isFalse();
        assertThat(emptyPosition.isOccupied()).isTrue();
    }

    @Test
    public void testMovePiecesOfDistintType(){
        positionOfPlayer2.setAdjacents(List.of(positionOfPlayer1));
        positionOfPlayer1.setAdjacents(List.of(positionOfPlayer2));
        positionOfPlayer1.setForSpy(true);
        emptyPosition.setForSpy(false);
        Mockito.lenient().when(customListingPositionService.getPresencePositions(player2.getId(), true)).thenReturn(List.of(positionOfPlayer1));
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class, ()->playerUsePositionService.movePiece(positionOfPlayer1, emptyPosition, player2));
    }

    @Test
    public void testMovePieceWithoutEnoughtPresence(){
        playerUsePositionService=
        new PlayerUsePositionService(positionRepository,playerRepository,customListingPositionService);
        assertThrows(Exception.class,()->playerUsePositionService.movePiece(positionOfPlayer2,emptyPosition,player1));
    }
}



    

    
    

