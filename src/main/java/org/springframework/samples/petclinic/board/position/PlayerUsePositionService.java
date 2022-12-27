package org.springframework.samples.petclinic.board.position;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.CheckPlayerUsePosition;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerUsePositionService {

    private PositionRepository positionRepository;

    private PlayerRepository playerRepository;

    private CustomListingPositionService customListingPositionService;

    //el constructor es necesario para la realización de los tests
    @Autowired
    public PlayerUsePositionService(PositionRepository positionRepository
    ,PlayerRepository playerRepository,CustomListingPositionService customListingPositionService){
        this.positionRepository=positionRepository;
        this.playerRepository=playerRepository;
        this.customListingPositionService=customListingPositionService;
    }

    @Transactional//
    public void save(Position p) throws DataAccessException{
        positionRepository.save(p);
    }

    @Transactional(rollbackFor = Exception.class)
    public void occupyTroopPosition(@Valid Position position,@Valid Player player,Boolean onlyAdjacentPositions)
     throws DataAccessException,Exception{
        CheckPlayerUsePosition.playerHasChooseAEmptyPosition(position);
        CheckPlayerUsePosition.playerHasChooseACorrectTypeOfPosition(position, false);
        if(onlyAdjacentPositions ){
            List<Position> playerPositions=customListingPositionService.getPresencePositions(player.getId(),false);
            CheckPlayerUsePosition.playerHasChooseAPositionUsingPresence(position, playerPositions);
        }
        player.setTroops(player.getTroops()-1);
        position.setPlayer(player);
        save(position);
    }

    @Transactional(rollbackFor =
     Exception.class)
    public void killTroop(@Valid Position position,@Valid Player player,Boolean onlyAdjacentPositions) throws DataAccessException
    ,Exception{
        CheckPlayerUsePosition.playerHasChooseAEmptyPosition(position);
        CheckPlayerUsePosition.playerHasChooseANotOwnedPosition(player, position);

        if(onlyAdjacentPositions ){
            List<Position> playerPositions=
            customListingPositionService.getPresencePositions(player.getId(),true);
            CheckPlayerUsePosition.playerHasChooseAPositionUsingPresence(position, playerPositions);
        }

        player.getTrophyHall().add(position.getPlayer());
        playerRepository.save(player);
        position.setPlayer(null);
        save(position);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void occupySpyPosition(@Valid Position position,@Valid Player player)
     throws DataAccessException,Exception{
        CheckPlayerUsePosition.playerHasChooseACorrectTypeOfPosition(position, true);
        List<Position> playerSpiesInSameCityOfChoosedPosition=
        positionRepository.findAnySpyOfAPlayerInACity(player.getId(),position.getCity().getId());
        CheckPlayerUsePosition.playerHasntAnySpyInChoosedPosition(playerSpiesInSameCityOfChoosedPosition);
        player.setSpies(player.getSpies()-1);
        playerRepository.save(player);
        position.setPlayer(player);
        save(position);
    }

    @Transactional(rollbackFor = Exception.class)
    public void returnPiece(@Valid Position position,@Valid Player player) throws DataAccessException,Exception{
        List<Position> playerPositions=
        customListingPositionService.getPresencePositions(player.getId(),true);
        CheckPlayerUsePosition.playerHasChooseAPositionUsingPresence(position, playerPositions);
        Player enemy=position.getPlayer();
        if(position.getForSpy()){
            enemy.setSpies(enemy.getSpies()+1);
        }else{
            enemy.setTroops(enemy.getTroops()+1);
        }
        playerRepository.save(enemy);
        position.setPlayer(null);
        save(position);
    }

    @Transactional(rollbackFor = Exception.class)
    public void movePiece(@Valid Position source,@Valid Position target,@Valid Player player) throws DataAccessException,Exception{
        List<Position> playerPositions=
        customListingPositionService.getPresencePositions(player.getId(),true);
        CheckPlayerUsePosition.playerHasChooseAPositionUsingPresence(source, playerPositions);
        CheckPlayerUsePosition.playerHasChooseTwoPositionsOfSameType(target, source);
        target.setPlayer(source.getPlayer());
        source.setPlayer(null);
        save(source);
        save(target);
    }

    @Transactional(rollbackFor =
    {Exception.class})
    public void supplantTroop(@Valid Position position,@Valid Player player,Boolean onlyAdjacencies) throws DataAccessException
    ,Exception{

        CheckPlayerUsePosition.playerHasChooseANotOwnedPosition(player, position);
        if(onlyAdjacencies){
            List<Position> playerPositions=
            customListingPositionService.getPresencePositions(player.getId(),true);
            CheckPlayerUsePosition.playerHasChooseAPositionUsingPresence(position, playerPositions);
        }
        player.setTroops(player.getTroops()-1);
        player.getTrophyHall().add(position.getPlayer());
        playerRepository.save(player);
        position.setPlayer(player);
        save(position);
    }
    
}
