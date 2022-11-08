package org.springframework.samples.petclinic.board.sector.path;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends CrudRepository<Path,Integer>{

    @Query("SELECT path.id FROM Path path")
    List<Integer> findAllPathId();

    @Query("select p from Path p")
    List<Path> findAll2();
    @Query("select p from Path p where p.firstCity = :city")
    List<Path> getExitPathsFromCity(@Param("city") City city);

    
    
}
