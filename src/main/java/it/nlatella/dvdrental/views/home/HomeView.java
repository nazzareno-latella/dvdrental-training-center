package it.nlatella.dvdrental.views.home;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import it.nlatella.dvdrental.views.main.MainView;

@Route(value = "home", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Home")
@CssImport("./views/home/home-view.css")
public class HomeView extends Div {

	private static final long serialVersionUID = 1L;

	public HomeView() {
		addClassName("home-view");
		add(new Text("Work in progress..."));
	}

}
