package org.springframework.samples.tyrantsOfTheUnderdark.position;


import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.CustomListingPositionService;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.Position;
import org.springframework.samples.tyrantsOfTheUnderdark.board.position.PositionServiceRepo;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.City;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityRepository;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.city.CityTemplate;
import org.springframework.samples.tyrantsOfTheUnderdark.board.sector.path.PathRepository;
import org.springframework.samples.tyrantsOfTheUnderdark.game.Game;
import org.springframework.samples.tyrantsOfTheUnderdark.game.GameService;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;
import org.springframework.samples.tyrantsOfTheUnderdark.player.PlayerService;
import org.springframework.stereotype.Service;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class CustomListingPositionServiceTest {

    @Autowired
     CustomListingPositionService customListingPositionService;

    @Mock
    private PositionServiceRepo positionServiceRepo;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private PathRepository pathRepository;




    private Player player1;

    private Game game;

    private Player player2;

    @BeforeEach
    void setup(){
        this.player1=new Player();
        player1.setId(40);
        this.player2=new Player();
        player2.setId(41);
        this.game=new Game();
        this.game.setId(7);
        this.game.setPlayers(List.of(player1,player2));
        this.player1.setGame(game);
        this.player2.setGame(game);
        CityTemplate cityTemplate1=new CityTemplate();
        cityTemplate1.setId(1);
        cityTemplate1.setStartingCity(true);
        CityTemplate cityTemplate2=new CityTemplate();
        cityTemplate2.setId(2);
        cityTemplate2.setStartingCity(true);
        City city1= City.of(cityTemplate1,game);
        City city2=City.of(cityTemplate2, game);
        city1.setId(1);
        city2.setId(2);


        Position spy1FromCity1=new Position();
        spy1FromCity1.setForSpy(true);
        spy1FromCity1.setPlayer(player1);
        spy1FromCity1.setCity(city1);
        Position spy2FromCity1=new Position();
        spy2FromCity1.setForSpy(true);
        spy2FromCity1.setCity(city1);
        Position spy1FromCity2=new Position();
        spy1FromCity2.setId(3);
        spy1FromCity2.setForSpy(true);
        spy1FromCity2.setCity(city2);
        Position spy2FromCity2=new Position();
        spy2FromCity2.setForSpy(true);
        spy2FromCity2.setCity(city2);
        city1.setPositions(List.of(spy1FromCity1,spy2FromCity1));
        city2.setPositions(List.of(spy1FromCity2,spy2FromCity2));
        Position troop1FromCity1=new Position();
        troop1FromCity1.setForSpy(false);
        troop1FromCity1.setId(5);
        troop1FromCity1.setCity(city1);
        Position troop2FromCity1=new Position();
        troop2FromCity1.setForSpy(false);
        troop2FromCity1.setId(6);
        troop2FromCity1.setCity(city1);
        troop1FromCity1.setAdjacents(List.of(spy1FromCity1,spy2FromCity1,troop2FromCity1));
        troop2FromCity1.setAdjacents(List.of(spy1FromCity1,spy2FromCity1,troop1FromCity1));
        spy1FromCity1.setAdjacents(List.of(spy2FromCity1,troop1FromCity1,troop2FromCity1));
        spy2FromCity1.setAdjacents(List.of(spy1FromCity1,troop1FromCity1,troop2FromCity1));
        troop1FromCity1.setPlayer(player2);


        Player white=new Player();
        white.setId(0);
        troop2FromCity1.setPlayer(white);
        Mockito.lenient().when(positionServiceRepo.getSpyPositionsOfPlayer(player1.getId(), game))
        .thenReturn(new ArrayList<>(List.of(spy1FromCity1)));
        Mockito.lenient().when(positionServiceRepo.getFreeSpyPositionsFromGame(game))
        .thenReturn(new ArrayList<>(List.of(spy2FromCity1,spy1FromCity2,spy2FromCity2)));
        Mockito.lenient().when(positionServiceRepo.getPlayerPositions(player1.getId())).thenReturn(List.of(spy1FromCity1));
        customListingPositionService=new CustomListingPositionService(positionServiceRepo,cityRepository,pathRepository);
    }

    @Test
    @Transactional
    public void testGetFreeSpyPositionsForPlayer(){
        List<Position> freeSpyPositionsForPlayer=
        this.customListingPositionService.getFreeSpyPositionsForPlayer(player1.getId(), game);
        assertThat(freeSpyPositionsForPlayer.size()).isEqualTo(2);
        for(Position position:freeSpyPositionsForPlayer){
            assertThat(position.getForSpy());
            assertThat(position.isOccupied()==false);
        }
        
    }

    @Test
    public void testGetPresencePositionsSearchingFreePositions(){
        List<Position> playerPositions=this.positionServiceRepo.getPlayerPositions(player1.getId());
        List<Position> freeAdjacentPositionsOfPlayer=
        this.customListingPositionService.getPresencePositions(player1.getId(), false);
        assertThat(freeAdjacentPositionsOfPlayer).isNotEmpty();
        for(Position position:freeAdjacentPositionsOfPlayer){
            assertThat(position.isOccupied()==false);
            assertThat(position.getAdjacents().containsAll(playerPositions));
        }
    }

    @Test
    public void testGetPresencePositionsSearchingEnemies(){
        List<Position> playerPositions=this.positionServiceRepo.getPlayerPositions(player1.getId());
        List<Position> freeAdjacentPositionsOfPlayer=
        this.customListingPositionService.getPresencePositions(player1.getId(), true);
        assertThat(freeAdjacentPositionsOfPlayer).isNotEmpty();
        for(Position position:freeAdjacentPositionsOfPlayer){
            assertThat(position.isOccupied());
            assertThat(position.getPlayer()).isNotEqualTo(player1);
            assertThat(position.getAdjacents().containsAll(playerPositions));
        }
    }

    @Test
    public void testGetWhiteAdjacentTroopsPositionsOfPlayer(){
        List<Position> playerPositions=this.positionServiceRepo.getPlayerPositions(player1.getId());
        List<Position> whiteAdjacentPositionsOfPlayer=
        this.customListingPositionService
        .getEnemyPositionsByTypeOfGame(player1.getId(), false, true, true, game);
        assertThat(whiteAdjacentPositionsOfPlayer).isNotEmpty();
        for(Position position:whiteAdjacentPositionsOfPlayer){
            assertThat(position.isOccupied());
            assertThat(position.getPlayer().isWhite());
            assertThat(position.getAdjacents().containsAll(playerPositions));
        }
    }
    @Test
    public void testGetAnotherPlayerAdjacentTroopsPositionsOfPlayer(){
        List<Position> playerPositions=this.positionServiceRepo.getPlayerPositions(player1.getId());
        List<Position> anotherPlayerAdjacentPositionsOfPlayer=
        this.customListingPositionService
        .getEnemyPositionsByTypeOfGame(player1.getId(), false, true, false, game);
        assertThat(anotherPlayerAdjacentPositionsOfPlayer).isNotEmpty();
        for(Position position:anotherPlayerAdjacentPositionsOfPlayer){
            assertThat(position.isOccupied());
            assertThat(position.getPlayer().isWhite()==false);
            assertThat(position.getAdjacents().containsAll(playerPositions));
        }
    }



    
}
