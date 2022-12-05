package org.springframework.samples.petclinic.map.position;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.map.sector.path.Path;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionServiceRepo {

    @Autowired
    private PositionRepository positionRepository;

    public List<Position> getPositions(){//
        return (List<Position>)positionRepository.findAll();
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
    public List<Position> getFreePositions() throws DataAccessException{
        return positionRepository.findAllPositionByPlayerIsNull();
    }
    @Transactional(readOnly = true)
    public List<Position> getFreeTroopPositions() throws DataAccessException{
        return positionRepository.findAllPositionsByPlayerIsNullAndForSpyFalse();
    }
    @Transactional(readOnly = true)
    public List<Position> getFreeSpyPositions() throws DataAccessException{
        return positionRepository.findAllPositionsByPlayerIsNullAndForSpyTrue();
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
    public List<Position> getSpyPositionsOfPlayer(Integer player_id){
        return positionRepository.findAllPositionByPlayerIdAndForSpyTrue(player_id);
    }

    @Transactional(readOnly = true)
    public List<Position> getAllEnemyPositionsOfPlayerByTypeOfPosition(Integer player_id,Boolean forSpy){
        return positionRepository
            .findAllEnemyPositionsByType(player_id,forSpy);
    }


    
}
