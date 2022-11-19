package org.springframework.samples.petclinic.card.action;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionComplexRepository extends CrudRepository<ActionComplex, Integer>{
    
    @Query("SELECT ac FROM ActionComplex ac WHERE ac.id = ?1")
    ActionComplex findActionComplexById(Integer id);
}
