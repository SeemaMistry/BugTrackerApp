package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;

@PageTitle("Tickets | Bug Tracker")
@Route(value="tickets")
@RolesAllowed({"USER", "ADMIN"})
public class TicketsList extends VerticalLayout implements HasUrlParameter<String> {
    TicketSystemService TTService;
    Grid<Ticket> grid = new Grid<>(Ticket.class);

    // store URLParameter name here
    String projectName;

    public TicketsList(TicketSystemService TTService) {
        this.TTService = TTService;
        H1 welcome = new H1("A list of all the tickets");
       // configureGrid();
        add(welcome);
    }



    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("subject", "dueDate");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String name) {
        // get a list of tickets via repo searching for all tickets by project name
       // grid.setitems(TTService.findTicketsByProjectName(name))
        this.projectName = name;
        addSpantoUI();
//        grid.setItems(TTService.findAllTickets(name));
        //List<Ticket> tickets =
       // projectName.setTitle(String.format("You are seeing the %s project", name));

    }

    private void addSpantoUI() {
        Span displayProjectName = new Span(this.projectName);
        setSizeFull();
        configureGrid();
        grid.setSizeFull();
        add(displayProjectName, grid);
        updateGrid();

    }

    private void updateGrid() {
        grid.setItems(TTService.findAllTickets(this.projectName));
    }


}
