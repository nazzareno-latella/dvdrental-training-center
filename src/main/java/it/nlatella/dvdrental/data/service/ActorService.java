package it.nlatella.dvdrental.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import it.nlatella.dvdrental.data.entity.Actor;
import it.nlatella.dvdrental.data.repository.ActorRepository;

@Service
public class ActorService extends CrudService<Actor, Integer> {

    private ActorRepository repository;

    public ActorService(@Autowired ActorRepository repository) {
        this.repository = repository;
    }

    @Override
    protected ActorRepository getRepository() {
        return repository;
    }

}
