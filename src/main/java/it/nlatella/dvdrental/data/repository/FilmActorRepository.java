package it.nlatella.dvdrental.data.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.nlatella.dvdrental.data.entity.Film;
import it.nlatella.dvdrental.data.entity.FilmActor;
import it.nlatella.dvdrental.data.entity.FilmActorPK;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorPK>, Serializable {

	Optional<List<FilmActor>> findByFilm(Film film);
	
}
