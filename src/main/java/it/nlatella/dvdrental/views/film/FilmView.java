package it.nlatella.dvdrental.views.film;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.vaadin.componentfactory.selectiongrid.SelectionGrid;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import it.nlatella.dvdrental.data.entity.Actor;
import it.nlatella.dvdrental.data.entity.Category;
import it.nlatella.dvdrental.data.entity.Film;
import it.nlatella.dvdrental.graphql.GraphQLTemplate;
import it.nlatella.dvdrental.views.main.MainView;

@Route(value = "film", layout = MainView.class)
@PageTitle("Film")
@CssImport("./views/film/film-view.css")
public class FilmView extends Div {

	private static final long serialVersionUID = 1L;

	private Grid<Film> grid = new SelectionGrid<>(Film.class, false);
	private ListDataProvider<Film> dataProvider;

	private TextField title;
	private TextArea categories;
	private TextArea actors;

	private Div detailLayoutDiv;

	public FilmView(@Autowired GraphQLTemplate graphQLTemplate) {
		addClassName("film-view");

		// Create UI
		SplitLayout splitLayout = new SplitLayout();
		splitLayout.setSizeFull();
		splitLayout.setSplitterPosition(80);

		createGridLayout(splitLayout);
		createDetailLayout(splitLayout);

		add(splitLayout);

		// Configure Grid
		grid.addColumn("title").setAutoWidth(true);
		grid.addColumn("description").setAutoWidth(true);
		grid.addColumn("releaseYear").setHeader("Year").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);
		grid.addColumn(new NumberRenderer<>(Film::getRentalRate, NumberFormat.getCurrencyInstance()))
				.setHeader("Rental rate").setAutoWidth(true).setTextAlign(ColumnTextAlign.CENTER);

		List<Film> films = graphQLTemplate
				.executeQuery("query films { films { filmId title description releaseYear rentalRate } }",
						Collections.emptyMap())
				.extractValueAsObject("data.films[*]", new TypeRef<List<Film>>() {
				});
		dataProvider = new ListDataProvider<>(films);
		grid.setDataProvider(dataProvider);

		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		grid.setHeightFull();

		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				GraphQLResponse graphQLResponse = graphQLTemplate.executeQuery(
						"query filmDetailById($id: ID) { filmDetailById(id: $id) { title categories { name } actors { firstName lastName } } }",
						Collections.singletonMap("id", event.getValue().getFilmId()));

				String title = graphQLResponse.extractValueAsObject("filmDetailById.title", String.class);
				List<Category> categories = graphQLResponse.extractValueAsObject("filmDetailById.categories[*]",
						new TypeRef<List<Category>>() {
						});
				List<Actor> actors = graphQLResponse.extractValueAsObject("filmDetailById.actors[*]",
						new TypeRef<List<Actor>>() {
						});

				// when a row is selected but the data is no longer available, refresh grid
				if (title != null) {
					detailLayoutDiv.setVisible(true);
					populateForm(title, categories, actors);
				} else {
					refreshGrid();
				}
			} else {
				clearForm();
			}
		});
	}

	private void createDetailLayout(SplitLayout splitLayout) {
		detailLayoutDiv = new Div();
		detailLayoutDiv.setId("detail-layout");
		detailLayoutDiv.setVisible(false);

		Div detailDiv = new Div();
		detailDiv.setId("detail");
		detailLayoutDiv.add(detailDiv);

		FormLayout formLayout = new FormLayout();
		title = new TextField("Title");
		title.setReadOnly(true);
		categories = new TextArea("Categories");
		categories.setReadOnly(true);
		actors = new TextArea("Actors");
		actors.setReadOnly(true);

		Component[] fields = new Component[] { title, categories, actors };

		for (Component field : fields) {
			((HasStyle) field).addClassName("full-width");
		}
		formLayout.add(fields);
		detailDiv.add(formLayout);

		splitLayout.addToSecondary(detailLayoutDiv);
	}

	private void createGridLayout(SplitLayout splitLayout) {
		TextField filterText = new TextField("Filter");
		filterText.setPlaceholder("Filter by title...");
		filterText.setClearButtonVisible(true);
		filterText.setValueChangeMode(ValueChangeMode.EAGER);
		filterText.addValueChangeListener(e -> updateList(e.getValue()));

		Div wrapper = new Div();
		wrapper.setId("grid-wrapper");
		wrapper.setWidthFull();
		splitLayout.addToPrimary(wrapper);
		wrapper.add(filterText);
		wrapper.add(grid);
	}

	private void updateList(String filter) {
		if (filter != null && !filter.isEmpty()) {
			dataProvider.setFilter(film -> film.getTitle().toLowerCase().contains(filter.toLowerCase()));
		} else {
			dataProvider.clearFilters();
		}
	}

	private void refreshGrid() {
		grid.select(null);
		grid.getDataProvider().refreshAll();
	}

	private void clearForm() {
		populateForm(null, null, null);
	}

	private void populateForm(String title, List<Category> categories, List<Actor> actors) {
		this.title.setValue(title);

		StringBuffer categoriesList = new StringBuffer("");
		if (categories != null && !categories.isEmpty()) {
			for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext();) {
				Category category = iterator.next();
				categoriesList.append(category.getName());
				if (iterator.hasNext()) {
					categoriesList.append("\n");
				}
			}
		}
		this.categories.setValue(categoriesList.toString());

		StringBuffer actorsList = new StringBuffer("");
		if (actors != null && !actors.isEmpty()) {
			for (Iterator<Actor> iterator = actors.iterator(); iterator.hasNext();) {
				Actor actor = iterator.next();
				actorsList.append(actor.getFirstName());
				actorsList.append(" ");
				actorsList.append(actor.getLastName());
				if (iterator.hasNext()) {
					actorsList.append("\n");
				}
			}
		}
		this.actors.setValue(actorsList.toString());
	}
}
