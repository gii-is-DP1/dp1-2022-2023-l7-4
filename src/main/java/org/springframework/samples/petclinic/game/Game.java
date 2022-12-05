package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.HalfDeck;
import org.springframework.samples.petclinic.map.Map;
import org.springframework.samples.petclinic.map.MapTemplate;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="games")
public class Game extends BaseEntity{

    @NotEmpty
    String name= "";

    @Temporal(TemporalType.DATE)
    Date date = new Date();
    
    @Column(name="is_finished",columnDefinition = "boolean default false")
    Boolean isFinished = false;
    
    Boolean loaded = false;
    @NotNull
    Integer size=0;

    @ManyToOne
    MapTemplate mapTemplate = new MapTemplate();

    @ManyToOne
    Map map = new Map();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game",fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "first_half_deck_id")
    private HalfDeck firstHalfDeck;

    @ManyToOne()
    @JoinColumn(name = "second_half_deck_id")
    private HalfDeck secondHalfDeck;

    @ManyToMany
    private List<Card> gameDeck = new ArrayList<>();
    
    @ManyToMany
    private List<Card> sellZone = new ArrayList<>();
    
    @ManyToMany
    private List<Card> devoured = new ArrayList<>();
    
    @ManyToMany
    private List<Card> houseGuards = new ArrayList<>();

    @ManyToMany
    private List<Card> lolths = new ArrayList<>();




    public void addPlayer(Player player) {
        getPlayers().add(player);
        player.setGame(this);
    }
    public void removePlayer(Player player) {
        players.remove(player);
        player.setGame(null);
    }

    public Boolean isLoaded(){
        return
        !(
            this.map.getCities().isEmpty() |
            this.map.getPaths().isEmpty()
        );
    }
}
