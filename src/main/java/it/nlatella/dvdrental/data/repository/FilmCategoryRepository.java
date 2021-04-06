package it.nlatella.dvdrental.data.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.nlatella.dvdrental.data.entity.Film;
import it.nlatella.dvdrental.data.entity.FilmCategory;
import it.nlatella.dvdrental.data.entity.FilmCategoryPK;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, FilmCategoryPK>, Serializable {

	Optional<FilmCategory> findByFilm(Film film);
	
}
