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
package org.springframework.samples.tyrantsOfTheUnderdark.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_USER_CREATE_FORM = "users/createUserForm";
	private static final String VIEWS_CURRENT_USER_DETAILS_FORM = "users/currentUserDetails";
	private static final String VIEWS_CHANGE_USER_PASSWORD_FORM = "users/currentUserChangePassword";





	@Autowired
	private UserService userService;


	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	@GetMapping(value = "/user/new")
	public String initCreationUserForm(Map<String, Object> model) {
		User user = new User();
		model.put("user", user);
		return VIEWS_USER_CREATE_FORM;
	}
    

	@PostMapping(value = "/user/new")
	public String processCreationUserForm(@Valid User user, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_USER_CREATE_FORM;
		}
		else {
			userService.saveUser(user);
			return "redirect:/";
		}
	}



	@GetMapping(value = "/users/list")
	public String proccesUsersListing(Map<String, Object> model){
		model.put("selections", userService.getUsers());
		return "users/usersList";
	}
	@GetMapping(value = "/users")
	public String processFindForm(User user, BindingResult result, Map<String, Object> model) {

		
		if (user.getName()== null) {
			user.setName("");
		}
		User result1 = this.userService.getUserByUsername(user.getName());
		List<User> results= new ArrayList<User>();
		results.add(result1);
		
		if (results.isEmpty()) {
			result.rejectValue("username", "notFound", "not found");
			return "users/findUser";
		}
		else if (results.size() == 1) {
			user = results.iterator().next();
			return "redirect:/users/" + user.getUsername();
		}
		else {
			model.put("selections", results);
			return "users/usersList";
		}
	}
	@GetMapping(value = "/users/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("user", new User());
		return "users/findUser";
	}
	@GetMapping("/users/{username}")
	public ModelAndView showUser(@PathVariable("username") String username) {
		ModelAndView mav = new ModelAndView("users/userDetails");
		mav.addObject(userService.getUserByUsername(username));
		return mav;
	}

	@GetMapping("/users/{username}/delete")
	public ModelAndView deleteUser(@PathVariable("username") String username){
		ModelAndView mav = new ModelAndView("redirect:/users/list");
		User user=this.userService.getUserByUsername(username);
		String canBe=user.canBeDeleted()?null:"No puedes borrar a un usuario que esta jugando todavía";
		mav.addObject("text", canBe);
		try{
			userService.deleteUser(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		return mav;
	}
	@GetMapping("/myprofile")
	public String currentUserProfile(Principal user, Model model){
		User currentUser =userService.getUserByUsername(user.getName());
		model.addAttribute(currentUser);
		return VIEWS_CURRENT_USER_DETAILS_FORM;

	}
	@PostMapping(value = "/myprofile")
	public String processUserProfile(@Valid User newuser, BindingResult result,Principal user) {
		if (result.hasErrors()) {
			return VIEWS_CURRENT_USER_DETAILS_FORM;
		}
		else {
			User currenUser = userService.getUserByUsername(user.getName());
			newuser.setUsername(currenUser.getUsername());
			userService.saveUser(newuser);
			return "redirect:/";
		}
	}

	@GetMapping("/users/{username}/edit")
	public ModelAndView editUserAsAdmin(@PathVariable("username") String username){
		User userToEdit =userService.getUserByUsername(username);
		ModelAndView res=new ModelAndView(VIEWS_CURRENT_USER_DETAILS_FORM);
		res.addObject("user", userToEdit);
		return res;

	}

	@PostMapping("/users/{username}/edit")
	public String processEditUserAsAdmin(@Valid User updateUser, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CURRENT_USER_DETAILS_FORM;
		}
		else {
			userService.saveUser(updateUser);
			return "redirect:/users/{username}";
		}
	}


	@GetMapping("/changepassword")
	public String changeUserPassword(Principal user, Model model){
		User currentUser =userService.getUserByUsername(user.getName());
		model.addAttribute(currentUser);
		return VIEWS_CHANGE_USER_PASSWORD_FORM;

	}
	@PostMapping(value = "/changepassword")
	public String processUserPassword(@Valid User newuser, BindingResult result,Principal user) {
		if (result.hasErrors()) {
			return VIEWS_CHANGE_USER_PASSWORD_FORM;
		}
		else {
			User currenUser = userService.getUserByUsername(user.getName());
			newuser.setUsername(currenUser.getUsername());
			userService.saveUser(newuser);
			return "redirect:/";
		}
	}

}
