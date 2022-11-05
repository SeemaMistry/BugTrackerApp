package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route("Ticket Tree Grid")
@PermitAll
public class TicketTreeGridView extends VerticalLayout {
    // list of tickets

    // services
    public TicketTreeGridView() {
        // create new treegrid

        // retrieve tickets from service

        // setItems in the treegrid (tickets, getAssignedEmployees()

        // add HierarchyColumn

        // add remaining columns

        // render treegrid to screen
    }

    // getAssignedEmployees(Ticket ticket) {}

}
