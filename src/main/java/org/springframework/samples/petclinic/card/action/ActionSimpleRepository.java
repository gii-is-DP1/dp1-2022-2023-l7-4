package org.springframework.samples.petclinic.card.action;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionSimpleRepository extends CrudRepository<ActionSimple, Integer>{

    @Query("SELECT ac FROM ActionSimple ac WHERE ac.id = ?1")
    ActionSimple findActionSimpleById(Integer id);
}
