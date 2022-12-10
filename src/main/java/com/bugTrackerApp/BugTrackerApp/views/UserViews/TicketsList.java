package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
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

    // Components: tickets grid, employee grid, ticket and employee search ComboBoxes
    Grid<Ticket> grid = new Grid<>(Ticket.class);
    // search for tickets based on employee ComboBox
    ComboBox<Employee> filterText = new ComboBox<>("Search Tickets by Employee");

    // store URLParameter Project object here
    String projectName;
    Project project;

    public TicketsList(TicketSystemService TTService, UserRelationsService URService) {
        this.TTService = TTService;
        this.URService = URService;
    }

    // display page content: grids and search ComboBoxes, and forms. Functions as constructor usually does
    private void getContent() {
        H1 welcome = new H1("A list of all the tickets for project: " + this.projectName);

        // configure grids
        setSizeFull();
        configureGrid();
        grid.setSizeFull();

        // display grids and update grids
        add(welcome, getToolbar(), grid);
        updateGrid();
    }

    // configure Ticket grid
    private void configureGrid() {
        grid.setSizeFull();
        // configure columns
        grid.setColumns("subject", "dueDate");
        grid.addColumn(e -> e.getTicketReporter().getFullName()).setHeader("Reporter");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    // configure the employee search ComboBox
    private HorizontalLayout getToolbar(){
        // find all employees assigned to this project
        filterText.setItems(URService.findAllEmployees(null));
        filterText.setItemLabelGenerator(Employee::getFullName);

        // update ticket grid when search ComboBox applied
        filterText.addValueChangeListener(e -> updateListBySearch());
        filterText.setPlaceholder("Search Employee");

        return new HorizontalLayout(filterText);
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

    // update ticket grid to find all the tickets
    private void updateGrid() {
        grid.setItems(TTService.findAllTickets(this.projectName));
    }

    // update ticket grid by employee search ComboBox value
    private void updateListBySearch(){
        grid.setItems(TTService.searchTicketByProjectAndEmployee(project, filterText.getValue()));
    }

}
