package org.springframework.samples.petclinic.position;


import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.position.CustomListingPositionService;
import org.springframework.samples.petclinic.board.position.Position;
import org.springframework.samples.petclinic.board.position.PositionServiceRepo;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.city.CityTemplate;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.player.Player;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class CustomListingPositionServiceTest {

    @Autowired
    protected CustomListingPositionService customListingPositionService;

    @Autowired
    private PositionServiceRepo positionServiceRepo;

    @Autowired
    private GameService gameService;


    private Player player1;

    private Game game;

    private Player player2;

    @BeforeEach
    void setup(){
        this.player1=new Player();
        player1.setId(1);
        this.player2=new Player();
        player2.setId(2);
        this.game=new Game();
        game.setId(1);
        CityTemplate cityTemplate1=new CityTemplate();
        cityTemplate1.setId(1);
        cityTemplate1.setStartingCity(true);
        City city1= City.of(cityTemplate1,game);
        City city2=new City();


        Position spy1FromCity1=new Position();
        spy1FromCity1.setForSpy(true);
        Position spy2FromCity1=new Position();
        spy2FromCity1.setForSpy(true);
        Position spy1FromCity2=new Position();
        spy1FromCity2.setForSpy(true);
        Position spy2FromCity2=new Position();
        spy2FromCity2.setForSpy(true);
        //List<Position> spyPlayerPositions=new ArrayList<>(List.of(position))
        //positionServiceRepo.getSpyPositionsOfPlayer(player_id,game);
        //positionServiceRepo.getFreeSpyPositionsFromGame(game)
    }

    @Test
    public void testGetFreeSpyPositionsForPlayer(){
        List<Position> freeSpyPositionsForPlayer=
        this.customListingPositionService.getFreeSpyPositionsForPlayer(player1.getId(), game); 

        
    }



    
}
