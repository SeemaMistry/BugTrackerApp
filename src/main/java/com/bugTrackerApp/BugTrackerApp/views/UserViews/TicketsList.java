package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Tickets | Bug Tracker")
@Route(value="tickets")
public class TicketsList extends VerticalLayout {
    public TicketsList() {
        H1 welcome = new H1("A list of all the tickets");
        add(welcome);
    }
}
