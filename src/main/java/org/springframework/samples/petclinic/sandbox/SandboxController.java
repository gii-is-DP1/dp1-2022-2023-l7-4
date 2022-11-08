package org.springframework.samples.petclinic.sandbox;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SandboxController {
    
    @RequestMapping(value = "sandbox")
    public List<String> prueba(){
        return List.of("a","b","b");
    }
}
