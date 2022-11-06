package org.springframework.samples.petclinic.board.sector.path;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends CrudRepository<Path,Integer>{

    @Query("SELECT path.id FROM Path path")
    List<Integer> findAllPathId();

    
    
}
