package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.List;

@Route("Ticket Tree Grid")
@PermitAll
public class TicketTreeGridView extends VerticalLayout {
    // list of tickets
    private List<Ticket> tickets;

    // services
    TicketSystemService TSService;
    UserRelationsService URService;
    public TicketTreeGridView(UserRelationsService URService, TicketSystemService TSService) {
        this.TSService = TSService;
        this.URService = URService;
        // create new treegrid

        // retrieve tickets from service

        // setItems in the treegrid (tickets, getAssignedEmployees()

        // add HierarchyColumn

        // add remaining columns

        // render treegrid to screen
    }

    // getAssignedEmployees(Ticket ticket) {}

}
