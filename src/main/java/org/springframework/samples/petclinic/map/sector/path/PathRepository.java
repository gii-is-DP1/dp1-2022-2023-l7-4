package org.springframework.samples.petclinic.map.sector.path;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.map.sector.city.City;
import org.springframework.stereotype.Repository;

@Repository
public interface PathRepository extends CrudRepository<Path,Integer>{

    @Query("SELECT path.id FROM Path path")
    List<Integer> findAllPathId();
    
    @Query("select p from Path p")
    List<Path> findAll();
    
    @Query("select p from Path p where p.firstCity = :city")
    List<Path> findExitPathsFromCity(@Param("city") City city);
    
    @Query("select p from Path p where p.secondCity = :city")
    List<Path> findIncomingPathsFromCity(@Param("city") City city);
    
}
