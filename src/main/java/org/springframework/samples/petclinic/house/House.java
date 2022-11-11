package org.springframework.samples.petclinic.house;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;

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