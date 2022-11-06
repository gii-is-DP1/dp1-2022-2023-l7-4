package org.springframework.samples.petclinic.board.position;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.board.sector.path.Path;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService {

    private PositionRepository positionRepository;

    @Autowired
    public PositionService(PositionRepository posRepo){
        this.positionRepository=posRepo;
    }

    public List<Position> getPositions(){
        return (List<Position>)positionRepository.findAll();
    }

    @Transactional
    public void save(Position p){
        positionRepository.save(p);
    }

    @Transactional(readOnly = true)
    public List<Position> getPositionsFromPath(int path_id){
        return positionRepository.findAllPositionByPathId(path_id);
    }
    @Transactional(readOnly = true)
    public List<Position> getPositionsFromCity(int city_id){
        return positionRepository.findAllPositionByCityId(city_id);
    }
    

     @Transactional(readOnly = true)
     public List<Position> getFreePositions() throws DataAccessException{
        return positionRepository.findAllPositionByOccupiedFalse();
    }

    public void saveAndFlush(Position p) {
        positionRepository.saveAndFlush(p);
    }

    public Position findPositionById(Integer id) {
        return positionRepository.findById2(id);
    }
    
}
