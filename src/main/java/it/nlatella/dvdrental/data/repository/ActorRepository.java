package it.nlatella.dvdrental.data.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.nlatella.dvdrental.data.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>, Serializable {

}
