package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.combobox.ComboBox;
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
    // Services
    TicketSystemService TTService;
    UserRelationsService URService;

    // Components: tickets ticketGrid, employee ticketGrid, ticket and employee search ComboBoxes
    Grid<Ticket> ticketGrid = new Grid<>(Ticket.class);
    Grid<Employee> employeeGrid = new Grid<>(Employee.class);
    // search for tickets based on employee ComboBox
    ComboBox<Employee> searchTicketsByEmployee = new ComboBox<>("Search Tickets by Employee");

    // store URLParameter Project object here
    String projectName;
    Project project;

    public TicketsList(TicketSystemService TTService, UserRelationsService URService) {
        this.TTService = TTService;
        this.URService = URService;
    }

    // HasURLParameter override: parse url string to retrieve and store Project object to class
    @Override
    public void setParameter(BeforeEvent beforeEvent, String name) {
        this.projectName = name.replaceAll("%20", " ");
        this.project = TTService.findProjectByName(projectName);

        // call getContent() to render components to webpage
        // getContent() acts as the constructor
        getContent();
    }

    // display page content: grids and search ComboBoxes, and forms. Functions as constructor usually does
    private void getContent() {
        H1 welcome = new H1("A list of all the tickets for project: " + this.projectName);

        // configure grids
        setSizeFull();
        configureTicketGrid();
        configureEmployeeGrid();
        ticketGrid.setSizeFull();
        employeeGrid.setSizeFull();

        // display grids and update grids
        add(welcome, getToolbar(), ticketGrid, employeeGrid);
        updateGrid();
    }

    // configure Ticket ticketGrid
    private void configureTicketGrid() {
        ticketGrid.setSizeFull();
        // configure columns
        ticketGrid.setColumns("subject", "dueDate");
        ticketGrid.addColumn(e -> e.getTicketReporter().getFullName()).setHeader("Reporter");
        ticketGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    // configure Employee employee grid
    private void configureEmployeeGrid(){
        employeeGrid.setSizeFull();
        // configure columns
        employeeGrid.setColumns("firstName", "lastName");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    // configure the employee search ComboBox
    private HorizontalLayout getToolbar(){
        // find all employees assigned to this project
        searchTicketsByEmployee.setItems(URService.findAllEmployees(null));
        searchTicketsByEmployee.setItemLabelGenerator(Employee::getFullName);

        // update ticket ticketGrid when search ComboBox applied
        searchTicketsByEmployee.addValueChangeListener(e -> updateListBySearch());
        searchTicketsByEmployee.setPlaceholder("Search Employee");

        return new HorizontalLayout(searchTicketsByEmployee);
    }

    // update ticket ticketGrid to find all the tickets
    private void updateGrid() {
        ticketGrid.setItems(TTService.findAllTickets(this.projectName));
    }

    // update ticket ticketGrid by employee search ComboBox value
    private void updateListBySearch(){
        ticketGrid.setItems(TTService.searchTicketByProjectAndEmployee(project, searchTicketsByEmployee.getValue()));
    }

}
