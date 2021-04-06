package it.nlatella.dvdrental.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import it.nlatella.dvdrental.data.entity.Film;
import it.nlatella.dvdrental.data.entity.FilmActor;
import it.nlatella.dvdrental.data.entity.FilmActorPK;
import it.nlatella.dvdrental.data.repository.FilmActorRepository;

@Service
public class FilmActorService extends CrudService<FilmActor, FilmActorPK> {

    private FilmActorRepository repository;

    public FilmActorService(@Autowired FilmActorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected FilmActorRepository getRepository() {
        return repository;
    }

    public Optional<List<FilmActor>> findByFilm(Film film) {
        return getRepository().findByFilm(film);
    }
}
