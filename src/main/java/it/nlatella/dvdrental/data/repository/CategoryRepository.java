package it.nlatella.dvdrental.data.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.nlatella.dvdrental.data.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, Serializable {

}
