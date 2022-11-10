//package com.bugTrackerApp.BugTrackerApp.views.UserViews;
//
//import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
//import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
//import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
//import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.treegrid.TreeGrid;
//import com.vaadin.flow.router.Route;
//
//import javax.annotation.security.PermitAll;
//import java.util.List;
//import java.util.stream.Stream;
//
//@Route("Ticket Tree Grid")
//@PermitAll
//public class TicketTreeGridView extends VerticalLayout {
//    // list of tickets
//    //List<Ticket> tickets;
//
//    // services
//    TicketSystemService TSService;
//    UserRelationsService URService;
//    public TicketTreeGridView(UserRelationsService URService, TicketSystemService TSService) {
//        this.TSService = TSService;
//        this.URService = URService;
//
//        // create new treegrid
//        TreeGrid<Ticket> treeGrid = new TreeGrid<>(Ticket.class);
//        // retrieve tickets from service
//        List<Ticket> tickets = TSService.findAllTickets(null);
//        // setItems in the treegrid (tickets, getAssignedEmployees()
//       // treeGrid.setItems(tickets, this::getAssignedEmployees);
//        // add HierarchyColumn
//
//        // add remaining columns
//       // treeGrid.addColumn(Employee::getFullName);
//
//        Grid<Ticket> testgrid = new Grid<>(Ticket.class);
//        testgrid.setItems(TSService.findAllTickets(null));
//
//        // render treegrid to screen
//        add(treeGrid, testgrid);
//    }
//
//
//
//    // getAssignedEmployees(Ticket ticket) {}
//    private List<Employee> getAssignedEmployees(Ticket ticket) {
//        return TSService.findAssignedEmployeesToTicket(ticket);
//    }
//
//
//
//}
