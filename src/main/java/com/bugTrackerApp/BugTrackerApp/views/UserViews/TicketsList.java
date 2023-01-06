package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.AdminViews.TicketForm;
import com.bugTrackerApp.BugTrackerApp.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;

@PageTitle("Tickets | Bug Tracker")
@Route(value="tickets", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class TicketsList extends VerticalLayout implements HasUrlParameter<String> {
    // Services
    TicketSystemService TTService;
    UserRelationsService URService;

    // Instantiate and define Components: grids, filters, buttons, forms

    // Grids: tickets and employeesAssigned
    Grid<Ticket> ticketGrid = new Grid<>(Ticket.class);
    Grid<Employee> employeeGrid = new Grid<>(Employee.class);

    // Filters: search by employee or Ticket.subject
    ComboBox<Employee> searchTicketsByEmployee = new ComboBox<>("Search Tickets by Employee");
    // search for tickets by subject
    TextField searchTicketsBySubject = new TextField("Search Ticket by Subject");

    //Buttons:  add new ticket and clear filters/searches
    Button addNewTicketBtn = new Button("Add Ticket");
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

    // configure and display page content: grids, filters, buttons and forms
    private void getContent() {
        H1 welcome = new H1("Tickets of: " + this.projectName);
        // remove excess margins
        welcome.getStyle()
                .set("margin-top", "30px")
                .set("margin-bottom", "0px");

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

    // configure ticketGrid
    private void configureTicketGrid() {
        ticketGrid.setSizeFull();
        // configure columns
        ticketGrid.setColumns("subject");
        ticketGrid.addColumn(e -> e.getFormattedCreatedDate()).setHeader("Created Date").setSortable(true);
        ticketGrid.addColumn(e -> e.getTicketType().getName()).setHeader("Type").setSortable(true);
        ticketGrid.addColumn(e -> e.getTicketReporter().getFullName()).setHeader("Reporter").setSortable(true);
        ticketGrid.addColumn(e -> e.getTicketPriority().getName()).setHeader("Priority").setSortable(true);
        ticketGrid.addColumn(e -> e.getTicketStatus().getName()).setHeader("Status").setSortable(true);
        ticketGrid.addColumn(e -> e.getTicketEstimatedTime().getEstimatedTime()).setHeader("Estimated Time").setSortable(true);
        ticketGrid.addColumn("dueDate");
        ticketGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // single select a ticket to see employees assigned to it
        ticketGrid.asSingleSelect().addValueChangeListener( e -> {
            // if ticket selected, populate employeeGrid with assigned employees, else close employeeGrid
            if (e.getValue() != null) { editTicket(e.getValue()); }
            else { closeEmployeeGrid(); }
        });
    }

    // configure employeeGrid
    private void configureEmployeeGrid(){
        employeeGrid.setSizeFull();
        // configure columns
        employeeGrid.setColumns("firstName", "lastName");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    // configure ticket Form
    private void configureTicketForm(){
        // initialize ticket form data and size
        ticketForm = new TicketForm(
                URService.findAllEmployeesAssignedToProject(project),
                URService.findAllEmployees(null),
                TTService.findAllTicketPriority(),
                TTService.findAllTicketEstimatedTimes(),
                TTService.findAllTicketType(),
                TTService.findAllStatuses()
        );

        // Use API  calls for save, delete, close events
        ticketForm.addListener(TicketForm.SaveEvent.class, this::saveTicket);
        ticketForm.addListener(TicketForm.DeleteEvent.class,  this::deleteTicket);
        ticketForm.addListener(TicketForm.CloseEvent.class, e -> {
            closeTicketForm();
            closeEmployeeGrid();
        });
    }

    // wrap ticketForm inside a Scroller
    private void configureScroller() {
        // set content and orientation orientation
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        VerticalLayout form = new VerticalLayout(ticketForm);
        scroller.setContent(form);
        // correct styling
        form.getStyle().set("padding", "0px");
        scroller.getStyle()
                .set("border", "1px solid grey");
    }

    // display ticket and emplopyee grids in horizontal layout
    private HorizontalLayout getGrids() {
        // display grids in horizontal layout
        HorizontalLayout grids = new HorizontalLayout(ticketGrid, employeeGrid);
        grids.setSizeFull();
        return grids;
    }

    // configure toolbar: filters and add new Ticket button
    private HorizontalLayout getToolbar(){
        // populate ComboBox with all employees assigned to this project
        searchTicketsByEmployee.setItems(URService.findAllEmployeesAssignedToProject(project));
        searchTicketsByEmployee.setItemLabelGenerator(Employee::getFullName);
        searchTicketsByEmployee.setPlaceholder("Search Employee");
        // update ticketGrid when search by employee applied
        searchTicketsByEmployee.addValueChangeListener(e -> updateListByEmployeeSearch());


        // configure search for Tickets based on Ticket.subject
        searchTicketsBySubject.setPlaceholder("Filter by subject...");
        searchTicketsBySubject.setClearButtonVisible(true);
        // update ticketGrid when search by Ticket.subject applied
        searchTicketsBySubject.setValueChangeMode(ValueChangeMode.LAZY);
        searchTicketsBySubject.addValueChangeListener(e -> updateListByTicketSubjectSearch());

        // add new ticket button
        addNewTicketBtn.addClickListener(e -> addTicket());

        // clear filters and close ticketForm and employeeGrid
        clearSearchBtn.addClickListener(e -> {
            searchTicketsByEmployee.clear(); // clear search comboBox
            updateGrid(); // go back to all tickets in grid
            closeTicketForm();
            closeEmployeeGrid();
        });

        // dynamically render toolbar based on current user session role
        HorizontalLayout toolbar = new HorizontalLayout();
        if (VaadinSession.getCurrent().getAttribute(User.class).getRole() == Role.ADMIN) {
            toolbar.add(addNewTicketBtn);
        }
        toolbar.add(searchTicketsByEmployee, searchTicketsBySubject, clearSearchBtn);
        // display toolbar in a clean line
        toolbar.setDefaultVerticalComponentAlignment(Alignment.END);
        return toolbar;
    }

    // return scroller
    private Scroller getScroller() {
        return this.scroller;
    }

    // update ticketGrid to find all the tickets
    private void updateGrid() {
        ticketGrid.setItems(TTService.findAllTickets(projectName));
    }

    // update ticketGrid by employee search ComboBox value
    private void updateListByEmployeeSearch(){
        ticketGrid.setItems(TTService.searchTicketByProjectAndEmployee(project, searchTicketsByEmployee.getValue()));
    }

    // update ticketGrid by Ticket.subject value
    private void updateListByTicketSubjectSearch(){
        ticketGrid.setItems(TTService.searchTicketBySubjectAndProject(searchTicketsBySubject.getValue(), this.project));
    }

    // close employeeGrid
    private void closeEmployeeGrid(){
        employeeGrid.setVisible(false);
        closeTicketForm();
    }

    // TICKET FORM MANIPULATIONS: save, delete, open and close

    // close ticketForm
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
        // no ticket = close form, else open form and employeeGrid and populate with ticket info
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
        // retrieve and set employees selected from MultiSelectComboBox to ticket.assignedEmployees
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
