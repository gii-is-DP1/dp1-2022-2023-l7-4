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
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "cards")
@Entity
public class Card {

    @NotBlank
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;


    @PositiveOrZero
    @NotBlank
    private Integer cost;

    @NotBlank
    private String story;

    @NotBlank
    @Column(name = "rules_text")        
    private String rulesText;

    @PositiveOrZero
    @NotBlank
    @Column(name = "deck_pv")        
    private Integer deckVP;

    @PositiveOrZero
    @NotBlank
    @Column(name = "inner_circle_pv")        
    private Integer innerCirclePV;

    @PositiveOrZero
    @NotBlank
    private Integer rarity;
    
    @ManyToOne
    @NotBlank
    @JoinColumn(name = "half_deck_id")
    private HalfDeck halfDeck;

    @ManyToOne
    @NotBlank
    @JoinColumn(name = "aspect_id")
    private Aspect aspect;
}


