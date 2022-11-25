package org.springframework.samples.petclinic.house;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HouseRepository extends CrudRepository<House,Integer>{
    
    @Query("SELECT house FROM House house WHERE house.name = ?1")
    public House findHousesByName(String name);

    House findById(int id) throws DataAccessException;
    
}
