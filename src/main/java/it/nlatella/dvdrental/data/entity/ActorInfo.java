package it.nlatella.dvdrental.data.entity;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;
import javax.persistence.*;


/**
 * The persistent class for the actor_info database table.
 * 
 */
@Entity
@Immutable
@Table(name="actor_info")
@NamedQuery(name="ActorInfo.findAll", query="SELECT a FROM ActorInfo a")
public class ActorInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="actor_id", updatable = false)
	private Integer actorId;

	@Column(name="film_info")
	private String filmInfo;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	public ActorInfo() {
	}

	public Integer getActorId() {
		return this.actorId;
	}

	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}

	public String getFilmInfo() {
		return this.filmInfo;
	}

	public void setFilmInfo(String filmInfo) {
		this.filmInfo = filmInfo;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}