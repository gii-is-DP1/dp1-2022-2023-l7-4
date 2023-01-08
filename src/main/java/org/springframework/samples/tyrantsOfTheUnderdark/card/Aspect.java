package org.springframework.samples.tyrantsOfTheUnderdark.card;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "aspects")
@Entity
public class Aspect {
    @NotNull
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String image;
    
    public static Aspect newAspect(){
        Aspect aspect=new Aspect();
        aspect.setName("hola");
        aspect.setDescription("cuidado que te pego");
        aspect.setImage("imagen.jpg");
        return aspect;
    }
}
