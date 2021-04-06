package it.nlatella.dvdrental.graphql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.InputArgument;

import it.nlatella.dvdrental.data.entity.Actor;
import it.nlatella.dvdrental.data.entity.Category;
import it.nlatella.dvdrental.data.entity.Film;
import it.nlatella.dvdrental.data.entity.FilmActor;
import it.nlatella.dvdrental.data.entity.FilmCategory;
import it.nlatella.dvdrental.data.repository.ActorRepository;
import it.nlatella.dvdrental.data.repository.CategoryRepository;
import it.nlatella.dvdrental.data.repository.FilmRepository;

@DgsComponent
public class GraphQLDataFetchers {

	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ActorRepository actorRepository;

	@DgsData(parentType = "Query", field = "films")
	public List<Film> getFilmsDataFetcher() {
		return filmRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
	}

	@DgsData(parentType = "Query", field = "filmDetailById")
	public Film getFilmDetailByIdDataFetcher(@InputArgument("id") String filmId) {
		return filmRepository.findById(Integer.parseInt(filmId)).get();
	}

	@DgsData(parentType = "Film", field = "categories")
	public List<Category> getCategoriesDataFetcher(DgsDataFetchingEnvironment dfe) {
		Film film = dfe.getSource();
		List<Category> categories = null;
		for (Iterator<FilmCategory> iterator = film.getFilmCategories().iterator(); iterator.hasNext();) {
			FilmCategory fc = iterator.next();

			if (categories == null) {
				categories = new ArrayList<>();
			}
			categories.add(categoryRepository.findById(fc.getCategory().getCategoryId()).get());
		}
		return categories;
	}

	@DgsData(parentType = "Film", field = "actors")
	public List<Actor> getActorsDataFetcher(DgsDataFetchingEnvironment dfe) {
		Film film = dfe.getSource();
		List<Actor> actors = null;
		for (Iterator<FilmActor> iterator = film.getFilmActors().iterator(); iterator.hasNext();) {
			FilmActor fa = iterator.next();

			if (actors == null) {
				actors = new ArrayList<>();
			}
			actors.add(actorRepository.findById(fa.getActor().getActorId()).get());
		}
		return actors;
	}
}
