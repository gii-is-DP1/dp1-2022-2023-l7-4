package org.springframework.samples.tyrantsOfTheUnderdark.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {

    private static final String VIEWS_OWNER_CREATE_FORM = "login";

  @RequestMapping("/login")
  public String login() {
    return VIEWS_OWNER_CREATE_FORM;
  }
}