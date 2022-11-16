package org.springframework.samples.petclinic.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.position.AdjacentPositionService;
import org.springframework.samples.petclinic.board.position.PopulatePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionRepository;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.samples.petclinic.board.position.exceptions.IncorrectPositionTypeException;
import org.springframework.samples.petclinic.board.position.exceptions.MoreThanOnePlayerSpyInSameCity;
import org.springframework.samples.petclinic.board.position.exceptions.NotEnoughPresence;
import org.springframework.samples.petclinic.board.position.exceptions.OccupiedPositionException;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityRepository;
import org.springframework.samples.petclinic.board.sector.path.Path;
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

    public City city1;

    public City city2;

    public Path path;

    public Player player1;

    public Player player2;
    public Position positionEmptyCity1;
    public Position positionOccupiedCity1;
    public Position spyPositionCity1;
    public Position positionOccupiedCity2;
    public Position positionpath;



    @BeforeEach
    public void setUp(){
        //hacer inicio de mock de positionRepo,cityRepo,pathRepo y playerRepo + constructor de position,player,city,path
        //con los
        

        //DATOS
        positionEmptyCity1=new Position(1,null,false,city1,null);
        positionOccupiedCity1=new Position(2,player1,false,city1,null);
        spyPositionCity1=new Position(3,null,true,city1,null);
        positionOccupiedCity2=new Position(4,player2,false,city2,null);
        positionpath=new Position(5,null,false,null,path);

        //PREPARACIÓN DE ATRIBUTOS
        positionOccupiedCity1.setAdjacents(List.of(positionEmptyCity1,positionpath));
        positionOccupiedCity2.setAdjacents(List.of(positionpath));
        List<Position> freePos=new ArrayList<>();
        freePos.addAll(List.of(positionEmptyCity1,spyPositionCity1,positionpath));
        //Mockito.lenient().when(positionRepository.findAllPositionByPlayerIsNull()).thenReturn(freePos);

    }



    //OJO, SOLO DETECTARÁ LAS POSICIONES EN EL DATA.SQL, CON POPULATE NO
    /* 
    @Test
    public void shouldFindPositionByCorrectId(){
        Position position1=this.positionService.findPositionById(1);
        assertThat(position1.getCity().getName()).startsWith("EL LABERINTO");
        assertThat(position1.getIsOccupied()).isFalse();
        assertThat(position1.getPath()).isNull();
        assertThat(position1.getForSpy()).isTrue();
    }
    @Test
    public void shouldFindAllPositions(){
        List<Position> allPositios=positionService.getPositions();
        assertThat(allPositios.size()).isGreaterThan(0);
    }

    //crear positionrepofake( en el beforeeach, haces un Positionrepo vacio, y luego creas por separado posiciones)
    @Test
    public void shouldFindAllFreePositions(){
        List<Position> freePositions=this.positionService.getFreePositions();
        assertThat(freePositions).isNotEmpty();
        assertThat(freePositions).allMatch(pos->pos.getIsOccupied()==false);
    }

    @Test
    public void shouldFindAllFreeTroopPositions(){
        List<Position> freePositions=this.positionService.getFreeTroopPositions();
        assertThat(freePositions).isNotEmpty();
        assertThat(freePositions).allMatch(pos->pos.getIsOccupied()==false & pos.getForSpy()==false);
    }

    @Test
    public void shouldFindAllFreeSpyPositions(){
        List<Position> freePositions=this.positionService.getFreeSpyPositions();
        assertThat(freePositions).isNotEmpty();
        assertThat(freePositions).allMatch(pos->pos.getIsOccupied()==false & pos.getForSpy());
    }

    @Test//lo mismo, pero creando un fakeplayerrepo, creas un player y verificas 
    public void shouldFindAllPositionsFromPlayerId(){
        List<Position> allPlayerPositions=this.positionService.getPlayerPositions(1);
        assertThat(allPlayerPositions).isNotEmpty();
        assertThat(allPlayerPositions)
        .allMatch(pos->pos.getIsOccupied() & pos.getPlayer().getName().equals("David"));
    }

    @Test
    public void shouldFindAllPositionsFromPathId(){
        List<Position> pathPositions=this.positionService.getPositionsFromPathId(2);
        assertThat(pathPositions).isNotEmpty();
        assertThat(pathPositions).allMatch(pos->pos.getPath().getFirstCity().getId()==1
         & pos.getPath().getSecondCity().getId()==3);       
    }
    */

    //que en el findTroopByPlayer este en el mock
    @Test
    public void testOccupyCorrectTroopPositionWithOutAdjacencies(){
            Player player =new Player();
            Integer numTroops=player.getTroops();
            Position emptyPos=new Position();
            emptyPos.setForSpy(false);
            PositionService positionService=new PositionService(positionRepository,playerRepository,cityRepository,pathRepository);
                    try {
                        positionService.occupyTroopPosition(emptyPos, player, false);
                        assertThat(emptyPos.getPlayer().getId()).isEqualTo(player.getId());
                        assertThat(emptyPos.getIsOccupied()).isTrue();
                        assertThat(player.getTroops()).isEqualTo(numTroops-1);
                    } catch (Exception e) {
                        fail("No deberia salir error");
                    }    
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
        PositionService positionService=new PositionService(positionRepository,playerRepository,cityRepository,pathRepository);
                try {
                    assertThat(positionService.getAdjacentTroopPositionsFromPlayer(player.getId(), false)).contains(emptyPos);
                    assertThat(positionService.getAdjacentTroopPositionsFromPlayer(player.getId(), false)).doesNotContain(adjPosToEmptyPos);
                    positionService.occupyTroopPosition(emptyPos, player, true);
                    assertThat(emptyPos.getPlayer().getId()).isEqualTo(player.getId());
                    assertThat(emptyPos.getIsOccupied()).isTrue();
                    assertThat(player.getTroops()).isEqualTo(numTroops-1);
                } catch (Exception e) {
                    fail("No deberia salir error");
                }    
    }

    @Test
    public void testOccupyTroopPositionAlreadyOccupy(){
        Player player1=new Player();
        Player player2=new Player();
        Position position=new Position();
        position.setPlayer(player2);
        PositionService positionService=new PositionService(positionRepository,playerRepository,cityRepository,pathRepository);
        assertThrows(OccupiedPositionException.class,()->positionService.occupyTroopPosition(position,player1,false));
    }

    @Test
    public void testOccupyIncorrectPosition(){
        Player player1=new Player();
        Position position=new Position();
        position.setForSpy(true);
        PositionService positionService=new PositionService(positionRepository,playerRepository,cityRepository,pathRepository);
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
        PositionService positionService=new PositionService(positionRepository,playerRepository,cityRepository,pathRepository);
        assertThrows(NotEnoughPresence.class,()->positionService.occupyTroopPosition(wantedPos,player,true));
    }
    @Test
    public void testOccupySpyPosition(){
            Player player =new Player();
            Integer numSpies=player.getSpies();
            Position emptyPos=new Position();
            City city=new City();
            emptyPos.setForSpy(true);
            emptyPos.setCity(city);
            PositionService positionService=new PositionService(positionRepository,playerRepository,cityRepository,pathRepository);
                        try {
                            positionService.occupySpyPosition(emptyPos, player);
                        } catch (DataAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IncorrectPositionTypeException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (MoreThanOnePlayerSpyInSameCity e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        assertThat(emptyPos.getPlayer().getId()).isEqualTo(player.getId());
                        assertThat(emptyPos.getIsOccupied()).isTrue();
                        assertThat(player.getSpies()).isEqualTo(numSpies-1);
                       
    }


    
}
