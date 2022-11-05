package org.springframework.samples.petclinic.board.sector.path;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.samples.petclinic.board.sector.Sector;

import lombok.Getter;
import lombok.Setter;



@Table(name = "paths")
@Entity
public class Path extends Sector{


}
