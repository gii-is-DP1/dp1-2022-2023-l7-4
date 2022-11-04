package org.springframework.samples.petclinic.board.position;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.board.sector.Sector;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "positions")
@Entity
public class Position{
    // @NotBlank
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    // @NotBlank
    private Integer zone;

    // @NotBlank
    private Boolean occupied;

    //RN- una posicion o pertenece a una ciudad o pertenece a un camino

    @OneToOne(optional=true)
    private Sector sector;
    static void print(String s){
        System.out.println(s);
    }

    @Override
    public String toString() {
        return id+" "+zone+" "+occupied;
    }
    
}
