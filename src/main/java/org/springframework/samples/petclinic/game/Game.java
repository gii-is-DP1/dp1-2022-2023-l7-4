package org.springframework.samples.petclinic.game;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenerationTime;
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

    @NotEmpty
    String Name;

    @NotNull
    Integer size;

    @Temporal(TemporalType.DATE)
    Date date;

    @Column(name="is_finished")
    Boolean isFinished;

    @OneToMany(mappedBy = "game")
    private Set<Player> player;

}
