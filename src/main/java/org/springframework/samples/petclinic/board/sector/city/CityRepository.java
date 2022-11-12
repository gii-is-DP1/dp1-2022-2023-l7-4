package org.springframework.samples.petclinic.board.sector.city;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City,Integer>{
    @Query("select c from City c")
    List<City> findAll2();
    
}
