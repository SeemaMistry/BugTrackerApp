package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Projects | Bug Tracker")
@Route(value="projects")
@RolesAllowed({"USER", "ADMIN"})
public class ProjectsList extends VerticalLayout {
    public ProjectsList() {
        H1 welcome = new H1("A list of all your projects");
        add(welcome);
    }
}
