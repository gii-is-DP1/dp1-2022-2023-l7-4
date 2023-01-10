package org.springframework.samples.tyrantsOfTheUnderdark.user;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.tyrantsOfTheUnderdark.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	
	@Id
	@NotBlank
	String username;

	@NotBlank
	String password;
	
	boolean enabled;

	@NotBlank
    String name;

    @Email
    @NotBlank
    String email;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
    LocalDate birthdate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private Set<Player> players;

	public Boolean canBeDeleted(){
		return players.isEmpty();
	}
}
