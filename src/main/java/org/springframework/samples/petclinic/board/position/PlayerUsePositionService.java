package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.position.auxiliarEntitys.CheckPlayerUsePosition;
import org.springframework.samples.petclinic.board.position.exceptions.EmptyPositionException;
import org.springframework.samples.petclinic.board.position.exceptions.IncorrectPositionTypeException;
import org.springframework.samples.petclinic.board.position.exceptions.MoreThanOnePlayerSpyInSameCity;
import org.springframework.samples.petclinic.board.position.exceptions.NotEnoughPresence;
import org.springframework.samples.petclinic.board.position.exceptions.OccupiedPositionException;
import org.springframework.samples.petclinic.board.position.exceptions.YourPositionException;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerUsePositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CustomListingPositionService customListingPositionService;

    @Transactional//
    public void save(Position p) throws DataAccessException{
        positionRepository.save(p);
    }

    @Transactional(rollbackFor = Exception.class)
    public void occupyTroopPosition(Position position,Player player,Boolean onlyAdjacentPositions)
     throws DataAccessException,Exception{
        CheckPlayerUsePosition.playerHasChooseAEmptyPosition(position);
        CheckPlayerUsePosition.playerHasChooseACorrectTypeOfPosition(position, false);
        CheckPlayerUsePosition.playerIsNotNull(player);
        if(onlyAdjacentPositions ){
            List<Position> playerPositions=customListingPositionService.getAdjacentPositionsFromPlayer(player.getId(),false);
            CheckPlayerUsePosition.playerHasChooseAPositionUsingPresence(position, playerPositions);
        }
        player.setTroops(player.getTroops()-1);
        position.setPlayer(player);
        save(position);
    }

    @Transactional(rollbackFor =
     Exception.class)
    public void killTroop(Position position,Player player,Boolean onlyAdjacentPositions) throws DataAccessException
    ,Exception{
        
        CheckPlayerUsePosition.playerIsNotNull(player);
        CheckPlayerUsePosition.playerHasChooseAPosition(position);
        CheckPlayerUsePosition.playerHasChooseANotEmptyPosition(position);
        CheckPlayerUsePosition.playerHasChooseANotOwnedPosition(player, position);

        if(onlyAdjacentPositions ){
            List<Position> playerPositions=
            customListingPositionService.getAdjacentPositionsFromPlayer(player.getId(),true);
            CheckPlayerUsePosition.playerHasChooseAPositionUsingPresence(position, playerPositions);
        }

        player.getTrophyHall().add(position.getPlayer());
        playerRepository.save(player);
        position.setPlayer(null);
        save(position);
    }

    @Transactional(rollbackFor = {IncorrectPositionTypeException.class,MoreThanOnePlayerSpyInSameCity.class})
    public void occupySpyPosition(Position position,Player player)
     throws DataAccessException,IncorrectPositionTypeException,MoreThanOnePlayerSpyInSameCity{
        if(position.getForSpy()==false){
            throw new IncorrectPositionTypeException();
        }else if(!positionRepository.findAnySpyOfAPlayerInACity(player.getId(),position.getCity().getId()).isEmpty())
            throw new MoreThanOnePlayerSpyInSameCity();
        player.setSpies(player.getSpies()-1);
        playerRepository.save(player);
        position.setPlayer(player);
        save(position);
    }

    @Transactional(rollbackFor = NotEnoughPresence.class)
    public void returnPiece(Position position,Player player) throws DataAccessException,NotEnoughPresence{
        if(!customListingPositionService.getAdjacentPositionsFromPlayer(player.getId(),true).contains(position))
            throw new NotEnoughPresence();
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
    public void movePiece(Position source,Position target) throws DataAccessException,Exception{
        CheckPlayerUsePosition.playerHasChooseAPosition(target);
        CheckPlayerUsePosition.playerHasChooseAPosition(source);
        CheckPlayerUsePosition.playerHasChooseTwoPositionsOfSameType(target, source);
        target.setPlayer(source.getPlayer());
        source.setPlayer(null);
        save(source);
        save(target);
    }

    @Transactional(rollbackFor =
    {EmptyPositionException.class,YourPositionException.class,NotEnoughPresence.class})
    public void supplantTroop(Position position,Player player,Boolean onlyAdjacencies) throws DataAccessException
    ,EmptyPositionException,YourPositionException,NotEnoughPresence{
        if(position.getPlayer()==null)
            throw new EmptyPositionException();
        else if(position.getPlayer().equals(player))
            throw new YourPositionException();
        else if(onlyAdjacencies
         & !customListingPositionService.getAdjacentPositionsFromPlayer(player.getId(),true).contains(position))
            throw new NotEnoughPresence();
        player.setTroops(player.getTroops()-1);
        player.getTrophyHall().add(position.getPlayer());
        playerRepository.save(player);
        position.setPlayer(player);
        save(position);
    }
    
}
