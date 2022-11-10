package org.springframework.samples.petclinic.market;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "actions")
@Entity
public class Market {
    @NotBlank
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
}
