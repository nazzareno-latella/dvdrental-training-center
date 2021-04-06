package it.nlatella.dvdrental.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import it.nlatella.dvdrental.data.entity.Film;
import it.nlatella.dvdrental.data.repository.FilmRepository;

@Service
public class FilmService extends CrudService<Film, Integer> {

    private FilmRepository repository;

    public FilmService(@Autowired FilmRepository repository) {
        this.repository = repository;
    }

    @Override
    protected FilmRepository getRepository() {
        return repository;
    }

}
