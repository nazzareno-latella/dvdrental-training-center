package it.nlatella.dvdrental.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import it.nlatella.dvdrental.data.entity.Category;
import it.nlatella.dvdrental.data.repository.CategoryRepository;

@Service
public class CategoryService extends CrudService<Category, Integer> {

    private CategoryRepository repository;

    public CategoryService(@Autowired CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    protected CategoryRepository getRepository() {
        return repository;
    }

}
