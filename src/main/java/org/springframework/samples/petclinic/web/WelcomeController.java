package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	 
		
		List<Person> persons=new ArrayList<Person>();
		Person person=new Person();
		person.setFirstName("José Antonio");
		person.setLastName("Parejo");
		persons.add(person);
		model.put("persons" , persons);
		model.put("title", "My proyect");
		model.put("group", "Teachers");
	    return "welcome";
	  }
}
