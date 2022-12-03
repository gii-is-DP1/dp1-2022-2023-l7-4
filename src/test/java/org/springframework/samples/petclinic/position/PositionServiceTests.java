package org.springframework.samples.petclinic.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionRepository;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.samples.petclinic.board.position.exceptions.EmptyPositionException;
import org.springframework.samples.petclinic.board.position.exceptions.IncorrectPositionTypeException;
import org.springframework.samples.petclinic.board.position.exceptions.MoreThanOnePlayerSpyInSameCity;
import org.springframework.samples.petclinic.board.position.exceptions.NotEnoughPresence;
import org.springframework.samples.petclinic.board.position.exceptions.OccupiedPositionException;
import org.springframework.samples.petclinic.board.position.exceptions.YourPositionException;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityRepository;
import org.springframework.samples.petclinic.board.sector.path.PathRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)

public class PositionServiceTests {

    @Mock
    private PlayerRepository playerRepository;
    
    @Mock
    private PositionRepository positionRepository;
    @Mock
    private CityRepository cityRepository;

    @Mock
    private PathRepository pathRepository;

    




    @Test
    public void testOccupyCorrectTroopPositionWithOutAdjacencies(){
            Player player =new Player();
            Integer numTroops=player.getTroops();
            Position emptyPos=new Position();
            emptyPos.setForSpy(false);
            PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
                    try {
                        positionService.occupyTroopPosition(emptyPos, player, false);
                    } catch (Exception e) {
                        fail("No deberia salir error");
                    }    
                    assertThat(emptyPos.getPlayer().getId()).isEqualTo(player.getId());
                    assertThat(emptyPos.getIsOccupied()).isTrue();
                    assertThat(player.getTroops()).isEqualTo(numTroops-1);
    }

    @Test
    public void testOccupyCorrectTroopPositionWithAdjacencies(){
        Player player =new Player();
        Integer numTroops=player.getTroops();
        Position emptyPos=new Position();
        Position playerPos=new Position();
        Position adjPosToEmptyPos=new Position();
        adjPosToEmptyPos.setAdjacents(new ArrayList<>(List.of(emptyPos)));
        playerPos.setPlayer(player);
        playerPos.setAdjacents(new ArrayList<>(List.of(emptyPos)));
        emptyPos.setForSpy(false);
        emptyPos.setAdjacents(new ArrayList<>(List.of(playerPos,adjPosToEmptyPos)));
        Mockito.lenient().when(positionRepository.findAllPositionByPlayerId(player.getId())).thenReturn(List.of(playerPos));
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThat(positionService.getAdjacentTroopPositionsFromPlayer(player.getId(), false)).contains(emptyPos);
        assertThat(positionService.getAdjacentTroopPositionsFromPlayer(player.getId(), false)).doesNotContain(adjPosToEmptyPos);
                try {
                    positionService.occupyTroopPosition(emptyPos, player, true);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
        assertThat(emptyPos.getPlayer().getId()).isEqualTo(player.getId());
        assertThat(emptyPos.getIsOccupied()).isTrue();
        assertThat(player.getTroops()).isEqualTo(numTroops-1);
            }
            
    @Test
    public void testOccupyTroopPositionAlreadyOccupy(){
        Player player1=new Player();
        Player player2=new Player();
        Position position=new Position();
        position.setPlayer(player2);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(OccupiedPositionException.class,()->positionService.occupyTroopPosition(position,player1,false));
    }

    @Test
    public void testOccupyIncorrectPosition(){
        Player player1=new Player();
        Position position=new Position();
        position.setForSpy(true);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(IncorrectPositionTypeException.class,()->positionService.occupyTroopPosition(position,player1,false));
    }

    @Test
    public void testOccupyTroopPositionWithoutPresence(){
        Player player=new Player();
        Position positionPlayer=new Position();
        Position emptyPos=new Position();
        Position wantedPos=new Position();
        emptyPos.setAdjacents(new ArrayList<>(List.of(positionPlayer,wantedPos)));
        positionPlayer.setPlayer(player);
        positionPlayer.setAdjacents(new ArrayList<>(List.of(emptyPos)));
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(NotEnoughPresence.class,()->positionService.occupyTroopPosition(wantedPos,player,true));
    }
    @Test
    public void testOccupySpyPosition(){
            Player player =new Player();
            player.setId(1);
            Integer numSpies=player.getSpies();
            Position emptyPos=new Position();
            City city=new City();
            city.setId(1);
            emptyPos.setForSpy(true);
            emptyPos.setCity(city);
            PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
                        try {
                            positionService.occupySpyPosition(emptyPos, player);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            fail("No deberia dar ninguna excepción");
                        } 
            assertThat(emptyPos.getPlayer().getId()).isEqualTo(player.getId());
            assertThat(emptyPos.getIsOccupied()).isTrue();
            assertThat(player.getSpies()).isEqualTo(numSpies-1);
    }

    @Test
    public void testOccupyTroopPositionWithASpy(){
        Player player =new Player();
        Position position=new Position();
        position.setPlayer(player);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(IncorrectPositionTypeException.class,()->positionService.occupySpyPosition(position, player));
    }

    @Test
    public void testOccupySpyPositionInSameCity(){
        Player player =new Player();
        player.setId(1);
        Position playerPos=new Position();
        Position position=new Position();
        City city=new City();
        city.setId(1);
        position.setForSpy(true);
        position.setCity(city);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        when(positionRepository.findAnySpyOfAPlayerInACity(player.getId(),city.getId())).thenReturn(List.of(playerPos));
        assertThrows(MoreThanOnePlayerSpyInSameCity.class,()->positionService.occupySpyPosition(position, player));
    }


    @Test
    public void testKillAnyTroop(){
        Player player1=new Player();
        Player player2=new Player();
        player1.setId(1);
        player2.setId(2);
        Integer expectPV=player2.getTrophyHall().size();
        Position position=new Position();
        position.setPlayer(player1);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThat(position.getIsOccupied()).isTrue();
        try {
            positionService.killTroop(position, player2, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(position.getIsOccupied()).isFalse();
        assertThat(player2.getTrophyHall().size()).isEqualTo(expectPV+1);
    }

    @Test
    public void testKillAdjTroop(){
        Player player1=new Player();
        Player player2=new Player();
        player1.setId(1);
        player2.setId(2);
        Integer expectPV=player2.getTrophyPV();
        Position wantedPosition=new Position();
        Position player2pos=new Position();
        player2pos.setPlayer(player2);
        player2pos.setAdjacents(new ArrayList<>(List.of(wantedPosition)));
        wantedPosition.setPlayer(player1);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        Mockito.lenient().when(positionRepository.findAllPositionByPlayerId(player2.getId())).thenReturn(List.of(player2pos));
        Mockito.lenient().when(positionRepository.findAllPositionByPlayerId(player1.getId())).thenReturn(List.of(wantedPosition));
        assertThat(wantedPosition.getIsOccupied()).isTrue();
        assertThat(positionService.getAdjacentTroopPositionsFromPlayer(player2.getId(), true)).contains(wantedPosition);
        try {
            positionService.killTroop(wantedPosition, player2, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(wantedPosition.getIsOccupied()).isFalse();
        assertThat(player2.getTrophyPV()).isEqualTo(expectPV+1);
    }

    @Test
    public void testKillEmptyPosition(){
        Position position=new Position();
        Player player=new Player();
        player.setId(1);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(EmptyPositionException.class,()->positionService.killTroop(position, player,false));
    }
    @Test
    public void testKillOwnPosition(){
        Position position=new Position();
        Player player=new Player();
        player.setId(1);
        position.setPlayer(player);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(YourPositionException.class,()->positionService.killTroop(position, player,false));
    }

    @Test
    public void testKillWithoutEnoughPresence(){
        Player player=new Player();
        Player enemy=new Player();
        Position positionPlayer=new Position();
        Position emptyPos=new Position();
        Position wantedPos=new Position();
        wantedPos.setPlayer(enemy);
        emptyPos.setAdjacents(new ArrayList<>(List.of(positionPlayer,wantedPos)));
        positionPlayer.setPlayer(player);
        positionPlayer.setAdjacents(new ArrayList<>(List.of(emptyPos)));
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(NotEnoughPresence.class,()->positionService.killTroop(wantedPos,player,true));
    }

    @Test
    public void testReturnPiece(){
        Player player1=new Player();
        Player player2=new Player();
        player1.setId(1);
        player2.setId(2);
        Integer expectTroops=player1.getTroops();
        Position wantedPosition=new Position();
        Position player2pos=new Position();
        player2pos.setPlayer(player2);
        player2pos.setAdjacents(new ArrayList<>(List.of(wantedPosition)));
        wantedPosition.setPlayer(player1);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        Mockito.lenient().when(positionRepository.findAllPositionByPlayerId(player2.getId())).thenReturn(List.of(player2pos));
        Mockito.lenient().when(positionRepository.findAllPositionByPlayerId(player1.getId())).thenReturn(List.of(wantedPosition));
        assertThat(wantedPosition.getIsOccupied()).isTrue();
        assertThat(positionService.getAdjacentTroopPositionsFromPlayer(player2.getId(), true)).contains(wantedPosition);
        try {
            positionService.returnPiece(wantedPosition, player2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(wantedPosition.getIsOccupied()).isFalse();
        assertThat(player1.getTroops()).isEqualTo(expectTroops+1);
    }

    @Test
    public void testReturnWithoutEnoughPresence(){
        Player player=new Player();
        Player enemy=new Player();
        Position positionPlayer=new Position();
        Position emptyPos=new Position();
        Position wantedPos=new Position();
        wantedPos.setPlayer(enemy);
        emptyPos.setAdjacents(new ArrayList<>(List.of(positionPlayer,wantedPos)));
        positionPlayer.setPlayer(player);
        positionPlayer.setAdjacents(new ArrayList<>(List.of(emptyPos)));
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(NotEnoughPresence.class,()->positionService.returnPiece(wantedPos,player));
    }

    @Test
    public void testSupplantAnyTroop(){
        Player player1=new Player();
        Player player2=new Player();
        player1.setId(1);
        player2.setId(2);
        Integer expectPV=player2.getTrophyPV();
        Position position=new Position();
        position.setPlayer(player1);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThat(position.getPlayer().getId()).isEqualTo(player1.getId());
        try {
            positionService.supplantTroop(position, player2, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(position.getPlayer().getId()).isEqualTo(player2.getId());
        assertThat(player2.getTrophyPV()).isEqualTo(expectPV+1);
    }
    @Test
    public void testSupplantAdjTroop(){
        Player player1=new Player();
        Player player2=new Player();
        player1.setId(1);
        player2.setId(2);
        Integer expectPV=player2.getTrophyPV();
        Position wantedPosition=new Position();
        Position player2pos=new Position();
        player2pos.setPlayer(player2);
        player2pos.setAdjacents(new ArrayList<>(List.of(wantedPosition)));
        wantedPosition.setPlayer(player1);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        Mockito.lenient().when(positionRepository.findAllPositionByPlayerId(player2.getId())).thenReturn(List.of(player2pos));
        Mockito.lenient().when(positionRepository.findAllPositionByPlayerId(player1.getId())).thenReturn(List.of(wantedPosition));
        assertThat(wantedPosition.getPlayer().getId()).isEqualTo(player1.getId());
        assertThat(positionService.getAdjacentTroopPositionsFromPlayer(player2.getId(), true)).contains(wantedPosition);
        try {
            positionService.supplantTroop(wantedPosition, player2, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            fail("No deberia salir ninguna excepción");
        } 
        assertThat(wantedPosition.getPlayer().getId()).isEqualTo(player2.getId());
        assertThat(player2.getTrophyPV()).isEqualTo(expectPV+1);
    }

    @Test
    public void testSupplantEmptyPosition(){
        Position position=new Position();
        Player player=new Player();
        player.setId(1);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(EmptyPositionException.class,()->positionService.supplantTroop(position, player,false));
    }
    @Test
    public void testSupplantOwnPosition(){
        Position position=new Position();
        Player player=new Player();
        player.setId(1);
        position.setPlayer(player);
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(YourPositionException.class,()->positionService.supplantTroop(position, player,false));
    }

    @Test
    public void testSupplantWithoutEnoughPresence(){
        Player player=new Player();
        Player enemy=new Player();
        Position positionPlayer=new Position();
        Position emptyPos=new Position();
        Position wantedPos=new Position();
        wantedPos.setPlayer(enemy);
        emptyPos.setAdjacents(new ArrayList<>(List.of(positionPlayer,wantedPos)));
        positionPlayer.setPlayer(player);
        positionPlayer.setAdjacents(new ArrayList<>(List.of(emptyPos)));
        PositionService positionService=new PositionService(positionRepository,playerRepository, null, null);
        assertThrows(NotEnoughPresence.class,()->positionService.supplantTroop(wantedPos,player,true));
    }


    
}
