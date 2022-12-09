package org.springframework.samples.petclinic.board.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionServiceRepo {

    @Autowired
    private PositionRepository positionRepository;

    public List<Position> getAllPositionsFromGame(Game game){//
        return (List<Position>)positionRepository.findAllPositionsByGameId(game.getId());
    }
    
    

    @Transactional(readOnly = true)
    public List<Position> getPositionsFromPath(Path path){
        return positionRepository.findAllPositionByPathId(path.getId());
    }
    public List<Position> getPositionsFromPathId(int path_id){
        return positionRepository.findAllPositionByPathId(path_id);
    }

    @Transactional(readOnly = true)
    public List<Position> getPositionsFromCityId(int city_id){
        return positionRepository.findAllPositionByCityId(city_id);
    }

    @Transactional(readOnly = true)
    public List<Position> getFreePositionsFromGame(Game game) throws DataAccessException{
        return positionRepository.findFreePositionsByGameId(game.getId());
    }
    @Transactional(readOnly = true)
    public List<Position> getFreeTroopPositionsFromGame(Game game) throws DataAccessException{
        return positionRepository.findFreeTroopPositionsByGameId(game.getId());
    }
    @Transactional(readOnly = true)
    public List<Position> getFreeSpyPositionsFromGame(Game game) throws DataAccessException{
        return positionRepository.findFreeSpyPositionsByGameId(game.getId());
    }

    @Transactional(readOnly = true) 
    public Position findPositionById(Integer id) {
        return positionRepository.findById2(id);
    }
    
    @Transactional(readOnly = true)
    public List<Position> getPlayerPositions(Integer player_id){
        return positionRepository.findAllPositionByPlayerId(player_id);
    }

    @Transactional(readOnly = true)
    public List<Position> getSpyPositionsOfPlayer(Integer player_id,Game game){
        return positionRepository.findSpyPositionsByPlayerIdAndByGameId(player_id,game.getId());
    }

    @Transactional(readOnly = true)
    public List<Position> getAllEnemyPositionsOfPlayerByTypeOfPosition(Integer player_id,Boolean forSpy,Game game){
        return positionRepository
            .findAllEnemyPositionsByPlayerIdAndByTypeAndByGameId(player_id,forSpy,game.getId());
    }



    public List<Position> getAllPositionsByGameID(Integer id) {
        return positionRepository.findAllPositionsByGameId(id);
    }


    
}
