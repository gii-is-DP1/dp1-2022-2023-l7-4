package org.springframework.samples.petclinic.position;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.board.position.AdjacentPositionService;
import org.springframework.samples.petclinic.board.position.PopulatePositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionService;
import org.springframework.samples.petclinic.board.sector.city.CityRepository;
import org.springframework.samples.petclinic.board.sector.path.PathRepository;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PositionServiceTests {

    @Autowired
    protected PositionService positionService;

    @Autowired
    protected PopulatePositionService populatePositionService;

    @Autowired
    protected AdjacentPositionService adjacentPositionService;

    @Autowired
    protected PlayerService playerService;




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
        assertThat(allPositios.size()).isEqualTo(7);
    }

    @Test
    public void shouldFindAllFreePositions(){
        List<Position> freePositions=this.positionService.getFreePositions();
        assertThat(freePositions.size()).isEqualTo(4);
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

    @Test
    public void shouldFindAllPositionsFromPlayerId(){
        List<Position> allPlayerPositions=this.positionService.getPlayerPositions(1);
        assertThat(allPlayerPositions).isNotEmpty();
        assertThat(allPlayerPositions)
        .allMatch(pos->pos.getIsOccupied() & pos.getPlayer().getName().equals("David"));
    }

    @Test
    public void shouldFindAllPositionsFromPathId(){
        List<Position> pathPositions=this.positionService.getPositionsFromPathId(2);
        assertThat(pathPositions.size()).isEqualTo(2);
        assertThat(pathPositions).allMatch(pos->pos.getPath().getFirstCity().getId()==1
         & pos.getPath().getSecondCity().getId()==3);       
    }

    @Test
    public void shouldPlaceTroopAValidPlayer(){


    }

    
}
