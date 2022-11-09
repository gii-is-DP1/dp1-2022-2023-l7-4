package org.springframework.samples.petclinic.market;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.card.Card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "markets")
@Entity
public class Market {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    private Boolean EmptyDeck;

    private Collection<Card> sellZone;

    @OneToMany()
    private Collection<Card> deck;

    @OneToMany()
    private Collection<Card> devoured;

    @OneToMany()
    private Collection<Card> houseguard;

    @OneToMany()
    private Collection<Card> lolth;




}
