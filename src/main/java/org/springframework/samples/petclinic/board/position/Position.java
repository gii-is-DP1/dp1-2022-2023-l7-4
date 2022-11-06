package org.springframework.samples.petclinic.board.position;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Check;
import org.springframework.samples.petclinic.board.sector.Sector;
import org.springframework.samples.petclinic.board.sector.city.City;
import org.springframework.samples.petclinic.board.sector.path.Path;

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

    @NotBlank
    private Integer zone;

    //poner atributo derivado: Boolean isPlayable

    @NotBlank
    private Boolean occupied;

    

    //RN- una posicion o pertenece a una ciudad o pertenece a un camino

    @ManyToOne(optional=true)
    @JoinColumn(name="city_id")
    private City city;

    @ManyToOne(optional = true)
    @JoinColumn(name="path_id")
    private Path path;

    @NotBlank
    @Column(name="for_spy")
    private Boolean forSpy;

    public Boolean getForSpy(){
        return city!=null;
    }
    //PARA ATRIBUTOS DERIVADOS HAY QUE CREAR TU PROPIO GET, si utilizas directamente el atributo,no funciona
    
    static void print(String s){
        System.out.println(s);
    }

//    @OneToMany
//    private List<Position> adjacents;

    @Override
    public String toString() {
        return id+" "+zone+" "+occupied;
    }

    public void checkSector(Integer city_id,Integer path_id){
        if(city_id!=null & path_id!=null)
            throw new IllegalAccessError("No puedes asignar una posición a 2 sectores a la vez");
    }

    
}
