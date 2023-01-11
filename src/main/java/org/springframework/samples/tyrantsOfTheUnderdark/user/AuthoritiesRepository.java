package org.springframework.samples.tyrantsOfTheUnderdark.user;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{

    @Query("SELECT auth FROM Authorities auth WHERE auth.authority = ?1")
    public List<Authorities> findAuthoritiesByName(String name);
	
}
