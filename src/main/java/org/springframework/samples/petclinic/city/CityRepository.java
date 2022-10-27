package org.springframework.samples.petclinic.city;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City,Integer>{
    
}
