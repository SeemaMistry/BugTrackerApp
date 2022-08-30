package com.bugTrackerApp.BugTrackerApp.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home | Bug Tracker")
@Route(value="")
public class HomeView extends VerticalLayout {
    public HomeView() {
        H1 welcome = new H1("Welcome to Bug Tracker");
        add(welcome);
    }
}
