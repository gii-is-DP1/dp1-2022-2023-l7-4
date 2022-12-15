/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.user;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	private AuthoritiesService authoritiesService;

	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepository.save(user);
		authoritiesService.saveAuthorities(user.getUsername(), "player");
	}
	
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}

	public Collection<User> getUsers(){
		return (Collection<User>) userRepository.findAll();

	}

	public User getUserByUsername(String username){
		return userRepository.findUserByUsername(username);
	}
	public User getUserByName(String name){
		return userRepository.findUserByName(name);
	}

	public void deleteUser(String username){
		userRepository.deleteById(username);

	}

	public List<User> getUsersList() {
		return (List<User>) userRepository.findAll();
	}

    public List<User> getAvailableUsers(Game game) {
		List<User> usersInGame = game.getPlayers().stream().map(player -> player.getUser()).collect(Collectors.toList());
        return getUsersList().stream().filter(user-> ! usersInGame.contains(user)).collect(Collectors.toList());
    }
}
