package it.nlatella.dvdrental.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import it.nlatella.dvdrental.views.main.MainView;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

	private static final long serialVersionUID = 1L;

	public AboutView() {
		addClassName("about-view");
		add(new Text("Work in progress..."));
	}

}
