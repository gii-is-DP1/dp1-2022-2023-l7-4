package org.springframework.samples.petclinic.game;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    public void addPlayer(Player player) {
        getPlayersInternal().add(player);
        player.setGame(this);
    }
        public void removePlayer(Player player) {
            players.remove(player);
            player.setGame(null);
        }
}
