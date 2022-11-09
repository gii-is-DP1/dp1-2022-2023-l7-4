package org.springframework.samples.petclinic.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "cards")
@Entity
public class Card {

    @NotNull
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;


    @PositiveOrZero
    @NotNull
    private Integer cost;

    @NotBlank
    private String story;

    @NotBlank
    @Column(name = "rules_text")        
    private String rulesText;

    @PositiveOrZero
    @NotNull
    @Column(name = "deck_pv")        
    private Integer deckVP;

    @PositiveOrZero
    @NotNull
    @Column(name = "inner_circle_pv")        
    private Integer innerCirclePV;

    @PositiveOrZero
    @NotNull
    private Integer rarity;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "half_deck_id")
    private HalfDeck halfDeck;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "aspect_id")
    private Aspect aspect;
}


