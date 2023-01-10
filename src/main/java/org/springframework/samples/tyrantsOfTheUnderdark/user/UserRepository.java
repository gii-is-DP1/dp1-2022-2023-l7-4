package org.springframework.samples.tyrantsOfTheUnderdark.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends  CrudRepository<User, String>{
    
    
    @Modifying
    @Query(value = "INSERT INTO games_users(game_id,users_id) VALUES(?1,?2)", nativeQuery = true)
    public void updateUsersGames(@Param(value = "gameId")Integer gameId, @Param(value = "userId")Integer userId);

    @Query("SELECT user FROM User user WHERE user.username = ?1")
    public User findUserByUsername(String username);

    @Query("SELECT user FROM User user WHERE user.name = ?1")
    public User findUserByName(String name);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(concat('%',:name,'%'))")
    List<User> findUsersByNamePageable(String name,Pageable pageable);
;
	
}
