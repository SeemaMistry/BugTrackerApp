package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import javax.annotation.security.RolesAllowed;

@PageTitle("Tickets | Bug Tracker")
@Route(value="tickets")
@RolesAllowed({"USER", "ADMIN"})
public class TicketsList extends VerticalLayout implements HasUrlParameter<String> {
    TicketSystemService TTService;
    Grid<Ticket> grid = new Grid<>(Ticket.class);

    // search for tickets based on employee ComboBox


    // store URLParameter name here
    String projectName;

    public TicketsList(TicketSystemService TTService) {
        this.TTService = TTService;
    }

    private void getContent() {
        H1 welcome = new H1("A list of all the tickets for project: " + this.projectName);
        setSizeFull();
        configureGrid();
        grid.setSizeFull();
        add(welcome, grid);
        updateGrid();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("subject", "dueDate");
        grid.addColumn(e -> e.getTicketReporter().getFullName()).setHeader("Reporter");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String name) {
        this.projectName = name.replaceAll("%20", " ");
        getContent();

    }

    private void updateGrid() {
        grid.setItems(TTService.findAllTickets(this.projectName));
    }


}
