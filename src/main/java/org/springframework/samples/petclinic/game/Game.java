package org.springframework.samples.petclinic.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.HalfDeck;
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
    String name;

    @NotNull
    Integer size;

    @Temporal(TemporalType.DATE)
    Date date;
    
    @Column(name="is_finished",columnDefinition = "boolean default false")
    Boolean isFinished = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game",fetch = FetchType.EAGER)
    private List<Player> players;

    protected List<Player> getPlayersInternal() {
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		return this.players;
	}
    
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
        getPlayersInternal().add(player);
        player.setGame(this);
    }
        public void removePlayer(Player player) {
            players.remove(player);
            player.setGame(null);
        }

        public void removePlayerByName(String name){
            
        }

    public void addPlayerInternal(Player player){
        getPlayersInternal().add(player);
    }
    public void addCurrentPlayer(Player player){
        getPlayersInternal().add(0, player);
        player.setGame(this);
    }
}
