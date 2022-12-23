package com.bugTrackerApp.BugTrackerApp.views;

import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.IconItem;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.List;
import javax.annotation.security.RolesAllowed;


@PageTitle("Home | Bug Tracker")
@Route(value="", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    TicketSystemService TSService;

    public HomeView(TicketSystemService TSService) {
        this.TSService = TSService;
        Card card = new Card(
                new TitleLabel("This is a card"),
                new PrimaryLabel("Some primary text"),
                new SecondaryLabel("Some secondary text"),
                new Item("Item title", "Item description"),
                new Actions(
                        new ActionButton("Action 1", event -> {/* Handle Action*/}),
                        new ActionButton("Action 2", event -> {/* Handle Action*/})
                )
        );
        Project project = TSService.findProjectByName("Animals App");
        Card projectCard = new Card(
                new TitleLabel(project.getName()),
                new PrimaryLabel(project.getDescription()),
                new SecondaryLabel(project.getProjectStatus().getName())
        );

        // will store all projects in a horizontalLayout
        HorizontalLayout projectCards = new HorizontalLayout();

        // get all projects
        List<Project> projectList = TSService.findAllProjects();
        // loop through all projects and set Labels
        for(Project p : projectList) {
            projectCards.add(new Card(
                    new TitleLabel(p.getName()),
                    new PrimaryLabel("Project Description:"),
                    new SecondaryLabel(p.getDescription()),
                    new Item("Project Status:", p.getProjectStatus().getName())

            ));
        }

        projectCards.setJustifyContentMode(JustifyContentMode.AROUND);
        HorizontalLayout projectCards2 = new HorizontalLayout(card, projectCard);
        projectCards2.setJustifyContentMode(JustifyContentMode.CENTER);
        add(projectCards, projectCards2);
    }

}
