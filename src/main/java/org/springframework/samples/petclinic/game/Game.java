package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="game")
public class Game {
    @Id
    @Column(name="id")
    private Integer id;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    LocalDate date;

    @Column(name="is_finished")
    Boolean isFinished;

    @OneToMany(mappedBy = "game")
    private Set<Player> player;



}
