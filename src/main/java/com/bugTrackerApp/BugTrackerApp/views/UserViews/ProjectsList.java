package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.AdminViews.ProjectForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import javax.annotation.security.RolesAllowed;
import java.awt.*;

@PageTitle("Projects | Bug Tracker")
@Route(value="projects")
@RolesAllowed({"USER", "ADMIN"})
public class ProjectsList extends VerticalLayout {
    Grid<Project> grid = new Grid<>(Project.class);
    TextField filterText = new TextField();

    ProjectForm projectForm;

    TicketSystemService TSService;
    UserRelationsService URService;
    public ProjectsList(TicketSystemService TSService,  UserRelationsService URService) {
        this.TSService = TSService;
        this.URService = URService;
        H1 welcome = new H1("A list of all your projects");

        //configure grid
        setSizeFull();
        configureGrid();
        configureForm();

        add(welcome, getContent());
        updateList();
    }

    private void configureForm() {
        projectForm = new ProjectForm(
                URService.findAllEmployees(null),
                TSService.findAllStatuses()
        );
        projectForm.setWidth("25em");
    }

    private void updateList() {
        grid.setItems(TSService.findAllProjects());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, projectForm);
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("name", "description");
        grid.addColumn(e -> e.getProjectStatus().getName()).setHeader("Status");
        grid.addColumn(e -> e.getCreator_employee().getFullName()).setHeader("Creator");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        // double click and be routed to tickets page
        grid.addItemDoubleClickListener(e ->
                UI.getCurrent().navigate(TicketsList.class, e.getItem().getName())
        );
    }
}
