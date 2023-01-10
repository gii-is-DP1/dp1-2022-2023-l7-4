package org.springframework.samples.tyrantsOfTheUnderdark.user;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends  CrudRepository<User, String>{
    
    

    @Query("SELECT user FROM User user WHERE user.username = ?1")
    public User findUserByUsername(String username);

    @Query("SELECT user FROM User user WHERE user.name = ?1")
    public User findUserByName(String name);
	
}
