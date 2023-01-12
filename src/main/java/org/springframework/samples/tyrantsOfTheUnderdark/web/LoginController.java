package org.springframework.samples.tyrantsOfTheUnderdark.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {
  
  private static final String VIEWS_OWNER_CREATE_FORM = "login";
  
  @RequestMapping("/login")
  public String login(Boolean hasErrors,Map<String,Object> model) {
    hasErrors = hasErrors==null?false:hasErrors;
    model.put("hasErrors", hasErrors);
    return VIEWS_OWNER_CREATE_FORM;
  }

  @RequestMapping("login-error")
  public String loginError(Map<String,Object> model) {
    model.put("hasErrors", true);
    return VIEWS_OWNER_CREATE_FORM;
  }

}