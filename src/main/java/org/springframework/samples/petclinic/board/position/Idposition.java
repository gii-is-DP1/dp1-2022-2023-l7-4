package org.springframework.samples.petclinic.board.position;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Idposition {
    
    @NotNull
    public Integer id;
}
