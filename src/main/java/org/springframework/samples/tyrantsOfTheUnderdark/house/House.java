package org.springframework.samples.tyrantsOfTheUnderdark.house;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="house")
public class House {

    @Id
    private Integer id;

    @Column(unique = true)
    String name;

    String description;

    String photo;

    @Column(name="hex_color")
    String hexColor;
    
}
