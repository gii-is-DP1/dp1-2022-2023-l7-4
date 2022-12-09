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
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotEmpty
    String Name;

    @NotNull
    Integer size;

    @Temporal(TemporalType.DATE)
    Date date;
    
    @Column(name="is_finished",columnDefinition = "boolean default false")
    Boolean isFinished = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game",fetch = FetchType.EAGER)
    private Set<Player> players;

    protected Set<Player> getPlayersInternal() {
		if (this.players == null) {
			this.players = new HashSet<>();
		}
		return this.players;
	}

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "GAMES_HALFDECK", 
    joinColumns = @JoinColumn(name = "GAME_ID"), 
    inverseJoinColumns = @JoinColumn(name = "HALFDECK_ID"))
    private List<HalfDeck> halfdecks = new ArrayList<HalfDeck>();

    protected List<HalfDeck> getHalfdecksInternal() {
		if (this.halfdecks == null) {
			this.halfdecks = new ArrayList<>();
		}
		return this.halfdecks;
	}

	protected void setHalfdecksInternal(List<HalfDeck> halfdecks) {
		this.halfdecks = halfdecks;
	}

    public int getNrOfhalfdecks() {
		return getHalfdecksInternal().size();
	}

	public void addSpecialty(HalfDeck halfDeck) {
		getHalfdecksInternal().add(halfDeck);
	}

    public List<HalfDeck> getHalfdecks(){
        List<HalfDeck> sortedPets = new ArrayList<>(getHalfdecksInternal());
		PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedPets);

    }
    
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
}
