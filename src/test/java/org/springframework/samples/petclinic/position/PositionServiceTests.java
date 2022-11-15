package org.springframework.samples.petclinic.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionRepository;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.samples.petclinic.board.position.exceptions.IncorrectPositionTypeException;
import org.springframework.samples.petclinic.board.position.exceptions.NotEnoughPresence;
import org.springframework.samples.petclinic.board.position.exceptions.OccupiedPositionException;
import org.springframework.samples.petclinic.board.sector.city.City;

import org.springframework.samples.petclinic.board.sector.path.Path;

import org.springframework.samples.petclinic.player.Player;

import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class PositionServiceTests {

    
    @Mock
    private PositionRepository positionRepository;
    
    protected PositionService positionService;

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
        positionService=new PositionService(positionRepository, null, null, null, null, null);
        player1=new Player();
        player1.setName("Manuel");
        player2=new Player();
        player2.setName("David");
        city1=new City();
        city1.setName("Villa Pesadilla");
        city2=new City();
        city2.setName("Laberinto");
        path=new Path();
        //DATOS
        positionEmptyCity1=new Position(1,null,false,city1,null);
        positionOccupiedCity1=new Position(2,player1,false,city1,null);
        spyPositionCity1=new Position(3,null,true,city1,null);
        positionOccupiedCity2=new Position(4,player2,false,city2,null);
        positionpath=new Position(5,null,false,null,path);

        //PREPARACIÓN DE ATRIBUTOS
        positionOccupiedCity1.setAdjacents(List.of(positionEmptyCity1,positionpath));
        positionOccupiedCity2.setAdjacents(List.of(positionpath));
        //when(positionService.getFreePositions()).thenReturn(List.of(positionEmptyCity1,spyPositionCity1,positionpath));
        //when(positionService.getAdjacentTroopPositionsFromPlayer(player1.getId(), false)).thenReturn(List.of(positionEmptyCity1,positionpath));

    }



    //OJO, SOLO DETECTARÁ LAS POSICIONES EN EL DATA.SQL, CON POPULATE NO
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

    //que en el findTroopByPlayer este en el mock
    @Test
    public void testOccupyCorrectTroopPositionWithOutAdjacencies(){
            positionEmptyCity1=new Position(1,null,false,city1,null);
            Integer numTroops=player1.getTroops();

                try {
                    this.positionService.occupyTroopPosition(positionEmptyCity1, player1, false);
                } catch (DataAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (OccupiedPositionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IncorrectPositionTypeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NotEnoughPresence e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            
            assertThat(positionEmptyCity1.getIsOccupied()).isTrue();
            assertThat(player1.getTroops()).isEqualTo(numTroops-1);
            assertThat(positionEmptyCity1.getPlayer().getId()).isEqualTo(player1.getId());
        

    }

    
}
