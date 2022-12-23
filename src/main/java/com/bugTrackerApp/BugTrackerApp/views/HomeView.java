package com.bugTrackerApp.BugTrackerApp.views;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.entity.Ticket;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.UserViews.ProfileView;
import com.bugTrackerApp.BugTrackerApp.views.UserViews.TicketsList;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.List;


@PageTitle("Home | Bug Tracker")
@Route(value="", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {
    // Services
    TicketSystemService TSService;
    UserRelationsService URService;

    // Components: form, grid
    FormLayout cardResponsiveFormLayout = new FormLayout();
    Grid<Ticket> ticketsGrid = new Grid<>(Ticket.class);

    // Employee entity
    Employee employee;

    public HomeView(TicketSystemService TSService, UserRelationsService URService) {
        this.TSService = TSService;

        // hard code an employee to test if grid is populating
        List<Employee> employeeList = URService.findAllEmployees(null);
        this.employee = employeeList.get(0);

        // configure components
        createProjectCards();
        configureCardResponsiveFormLayout();
        configureTicketGrid();

        add(new H1(this.employee.getFullName()), cardResponsiveFormLayout, ticketsGrid);
    }

    // create project cards and add them to the FormLayout
    private void createProjectCards(){
        // get all projects
        List<Project> projectList = TSService.findAllProjects();

        // loop through all projects to create cards with labels
        for(Project p : projectList) {
            Card newProjectCard = new Card(
                    new TitleLabel(p.getName()),
                    new PrimaryLabel("Project Description:"),
                    new SecondaryLabel(p.getDescription()),
                    new Item("Project Status:", p.getProjectStatus().getName()),
                    new Actions(
                            new ActionButton("Project", e -> {UI.getCurrent().navigate("projects");}),
                            new ActionButton("Tickets", e -> {UI.getCurrent().navigate(TicketsList.class, p.getName());}))
            );
            // set styling
            newProjectCard.setAlignItems(Alignment.END);
            newProjectCard.getStyle().set("margin", "10px");
            // add to cardResponsiveFormLayout
            cardResponsiveFormLayout.add(newProjectCard);
        }
    }

    // configure Form responsive Step layout
    private void configureCardResponsiveFormLayout(){
        cardResponsiveFormLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if the layout's width exceeds 320px
                new FormLayout.ResponsiveStep("320px", 2),
                // Use three columns, if the layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 3),
                // Use four columns, if the layout's width exceeds 900px
                new FormLayout.ResponsiveStep("900px", 4)
        );
    }

    private void configureTicketGrid(){
        // populate ticketGrid
        ticketsGrid.setItems(TSService.findTicketsAssignedToEmployee(this.employee));

        // configure columns (include project name)
        ticketsGrid.setColumns("subject");
        ticketsGrid.addColumn(e -> e.getFormattedCreatedDate()).setHeader("Created Date").setSortable(true);
        ticketsGrid.addColumn(e -> e.getTicketType().getName()).setHeader("Type").setSortable(true);
        ticketsGrid.addColumn(e -> e.getProject().getName()).setHeader("Project").setSortable(true);
        ticketsGrid.addColumn(e -> e.getTicketReporter().getFullName()).setHeader("Reporter").setSortable(true);
        ticketsGrid.addColumn(e -> e.getTicketPriority().getName()).setHeader("Priority").setSortable(true);
        ticketsGrid.addColumn(e -> e.getTicketStatus().getName()).setHeader("Status").setSortable(true);
        ticketsGrid.addColumn(e -> e.getTicketEstimatedTime().getEstimatedTime()).setHeader("Estimated Time").setSortable(true);
        ticketsGrid.addColumn("dueDate");
        ticketsGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
