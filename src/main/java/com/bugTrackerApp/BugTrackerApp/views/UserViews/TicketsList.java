package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.AdminViews.TicketForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
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

    // Ticket form and scroll wrapper
    TicketForm ticketForm;
    Scroller scroller = new Scroller();

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

        // configure form
        configureTicketForm();

        // configure Scroller (wraps ticketForm inside)
        configureScroller();

        // display grids and update grids
        add(welcome, getToolbar(), getScroller(), getGrids());
        updateGrid();
        closeEmployeeGrid();
    }

    // configure Ticket ticketGrid
    private void configureTicketGrid() {
//        ticketGrid.setSizeFull();
        // configure columns
        ticketGrid.setColumns("subject", "dueDate");
        ticketGrid.addColumn(e -> e.getTicketReporter().getFullName()).setHeader("Reporter");
        ticketGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // single select a ticket to see employees assigned
        ticketGrid.asSingleSelect().addValueChangeListener( e -> {
            // if ticket selected, populate employeeGrid with assigned employees, else close employeeGrid
            if (e.getValue() != null) { populateEmployeeGrid(e.getValue()); }
            else { closeEmployeeGrid(); }
        });
    }

    // configure Employee employee grid
    private void configureEmployeeGrid(){
//        employeeGrid.setSizeFull();
        // configure columns
        employeeGrid.setColumns("firstName", "lastName");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getGrids() {
        // label grids
        VerticalLayout labelledTicketGrid = new VerticalLayout(new H3("Tickets for " + this.projectName), ticketGrid);
        VerticalLayout labelledEmployeeGrid = new VerticalLayout(new H3("Assigned Employees"), employeeGrid);
        // display grids in horizontal layout
        HorizontalLayout grids = new HorizontalLayout(labelledTicketGrid, labelledEmployeeGrid);
        grids.setSizeFull();
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

    // configure ticket form
    private void configureTicketForm(){
        // initialize ticket form data and size
        ticketForm = new TicketForm(
                URService.findAllEmployees(null),
                TTService.findAllTicketPriority(),
                TTService.findAllTicketEstimatedTimes(),
                TTService.findAllTicketType(),
                TTService.findAllStatuses()
        );
//        ticketForm.setSizeFull();
    }

    // wrap ticketForm inside a Scroller
    private void configureScroller() {
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        VerticalLayout form = new VerticalLayout(ticketForm);
        form.getStyle().set("padding", "0px");
        scroller.setContent(form);
        scroller.getStyle()
                .set("border", "1px solid grey");
    }

    // return scroller
    private Scroller getScroller() {
        return this.scroller;
    }

    // update ticket ticketGrid to find all the tickets
    private void updateGrid() {
        ticketGrid.setItems(TTService.findAllTickets(this.projectName));
    }

    // update ticket ticketGrid by employee search ComboBox value
    private void updateListBySearch(){
        ticketGrid.setItems(TTService.searchTicketByProjectAndEmployee(project, searchTicketsByEmployee.getValue()));
    }

    // populate employeeGrid with assigned employees
    private void populateEmployeeGrid(Ticket ticket){
        employeeGrid.setItems(ticket.getEmployeesAssignedToTicket());
        employeeGrid.setVisible(true);
    }

    private void closeEmployeeGrid(){
        employeeGrid.setVisible(false);
    }

}
