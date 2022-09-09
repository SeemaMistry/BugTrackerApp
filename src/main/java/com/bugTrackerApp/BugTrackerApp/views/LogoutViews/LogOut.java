package com.bugTrackerApp.BugTrackerApp.views.LogoutViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Logout | Bug Tracker")
@Route(value="logout")
public class LogOut extends VerticalLayout {

    public LogOut() {
        H1 welcome = new H1("You have logout!");
        add(welcome);
    }
}
