package it.nlatella.dvdrental.data.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import it.nlatella.dvdrental.data.entity.Film;
import it.nlatella.dvdrental.data.entity.FilmCategory;
import it.nlatella.dvdrental.data.entity.FilmCategoryPK;
import it.nlatella.dvdrental.data.repository.FilmCategoryRepository;

@Service
public class FilmCategoryService extends CrudService<FilmCategory, FilmCategoryPK> {

    private FilmCategoryRepository repository;

    public FilmCategoryService(@Autowired FilmCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    protected FilmCategoryRepository getRepository() {
        return repository;
    }

    public Optional<FilmCategory> findByFilm(Film film) {
        return getRepository().findByFilm(film);
    }
}
