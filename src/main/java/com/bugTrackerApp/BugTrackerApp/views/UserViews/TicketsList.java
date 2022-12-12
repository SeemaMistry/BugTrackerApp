package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.AdminViews.TicketForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
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

    // add new ticket button
    Button addNewTicketBtn = new Button("Add Ticket");

    // clear search button
    Button clearSearchBtn = new Button("Clear Search");

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

        // call getContent() (acts as the constructor) to render components to webpage
        getContent();
    }

    // display page content: grids and search ComboBoxes, and forms. Functions as constructor usually does
    private void getContent() {
        H1 welcome = new H1("A list of all the tickets for project: " + this.projectName);

        // configure grids
        setSizeFull();
        configureTicketGrid();
        configureEmployeeGrid();

        // configure form
        configureTicketForm();

        // configure Scroller (wraps ticketForm inside)
        configureScroller();

        // display grids and update grids
        add(welcome, getToolbar(), getScroller(), getGrids());
        updateGrid();
        closeEmployeeGrid();
        closeTicketForm();
    }

    // configure Ticket ticketGrid
    private void configureTicketGrid() {
        ticketGrid.setSizeFull();
        // configure columns
        ticketGrid.setColumns("subject", "dueDate");
        ticketGrid.addColumn(e -> e.getTicketReporter().getFullName()).setHeader("Reporter");
        ticketGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // single select a ticket to see employees assigned
        ticketGrid.asSingleSelect().addValueChangeListener( e -> {
            // if ticket selected, populate employeeGrid with assigned employees, else close employeeGrid
            if (e.getValue() != null) { editTicket(e.getValue()); }
            else { closeEmployeeGrid(); }
        });
    }

    // configure Employee employee grid
    private void configureEmployeeGrid(){
        employeeGrid.setSizeFull();
        // configure columns
        employeeGrid.setColumns("firstName", "lastName");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
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

        // Use API  calls for save, delete, close events
        ticketForm.addListener(TicketForm.SaveEvent.class, this::saveTicket);
        ticketForm.addListener(TicketForm.DeleteEvent.class,  this::deleteTicket);
        ticketForm.addListener(TicketForm.CloseEvent.class, e -> closeTicketForm());
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

    private HorizontalLayout getGrids() {
        // label grids
//        this.ticketGridLabel.setText(this.ticketGridLabel.getText() + this.projectName);
//        VerticalLayout labelledTicketGrid = new VerticalLayout(this.ticketGridLabel, ticketGrid);
//        VerticalLayout labelledEmployeeGrid = new VerticalLayout(this.employeeGridLabel, employeeGrid);

        // display grids in horizontal layout
        HorizontalLayout grids = new HorizontalLayout(ticketGrid, employeeGrid);
//        if (labelledEmployeeGrid.isVisible()) {
//            H1 test = new H1("The employee grid is visible");
//            grids.add(test, labelledTicketGrid, labelledEmployeeGrid);
//        } else {
//            H1 test = new H1("The employee grid is NOT visible");
//            grids.add(test, labelledTicketGrid);
//        }
        grids.setSizeFull();
        return grids;
    }

    // configure the employee search ComboBox
    private HorizontalLayout getToolbar(){
        // find all employees assigned to this project
        searchTicketsByEmployee.setItems(URService.findAllEmployees(null));
        searchTicketsByEmployee.setItemLabelGenerator(Employee::getFullName);

        // update ticket ticketGrid when search ComboBox applied
        searchTicketsByEmployee.addValueChangeListener(e -> updateListBySearch());
        searchTicketsByEmployee.setPlaceholder("Search Employee");

        // add new ticket button with listener for addTicket()
        addNewTicketBtn.addClickListener(e -> addTicket());

        // clear search and close form and employee grid
        clearSearchBtn.addClickListener(e -> {
            searchTicketsByEmployee.clear(); // clear search comboBox
            updateGrid(); // go back to all tickets in grid
            closeTicketForm();
            closeEmployeeGrid();
        });

        HorizontalLayout toolbar = new HorizontalLayout(addNewTicketBtn, searchTicketsByEmployee, clearSearchBtn);
        // display toolbar in a clean line
        toolbar.setDefaultVerticalComponentAlignment(Alignment.END);
        return toolbar;
    }

    // return scroller
    private Scroller getScroller() {
        return this.scroller;
    }

    // update ticket ticketGrid to find all the tickets
    private void updateGrid() {
        ticketGrid.setItems(TTService.findAllTickets(projectName));
    }

    // update ticket ticketGrid by employee search ComboBox value
    private void updateListBySearch(){
        ticketGrid.setItems(TTService.searchTicketByProjectAndEmployee(project, searchTicketsByEmployee.getValue()));
    }

    private void closeEmployeeGrid(){
        employeeGrid.setVisible(false);
        closeTicketForm();
    }

    // Form Manipulation: save, delete, open and close
    private void closeTicketForm() {
        // clear form and close it
        ticketForm.setTicket(null);
        ticketForm.setVisible(false);
    }

    // add a new ticket
    private void addTicket() {
        Ticket newTicket = new Ticket();
        // pass in this project for field validation when saving ticket (cannot be null)
        newTicket.setProject(this.project);
        // call editTicket with new ticket object
        editTicket(newTicket);
    }

    // populate form and employeeGrid for editing
    private void editTicket(Ticket ticket){
        // no ticket = close form, else open form and populate form with ticket info
        if (ticket == null) {
            closeTicketForm();
        } else {
            ticketForm.setTicket(ticket);
            employeeGrid.setItems(ticket.getEmployeesAssignedToTicket());
            employeeGrid.setVisible(true);
            ticketForm.setVisible(true);
        }
    }

    // save ticket
    private void saveTicket(TicketForm.SaveEvent e) {
        // retrieve and set employees selected from MutliSelectComboBox to ticket
        e.getTicket().setEmployeesAssignedToTicket(ticketForm.getEmployeesAssigned());
        TTService.saveTicket(e.getTicket());
        updateGrid();
        closeTicketForm();
        closeEmployeeGrid();
    }

    // delete ticket
    private void deleteTicket(TicketForm.DeleteEvent e) {
        TTService.deleteTicket(e.getTicket());
        updateGrid();
        closeEmployeeGrid();
        closeTicketForm();

    }

}
