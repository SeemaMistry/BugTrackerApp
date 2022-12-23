package com.bugTrackerApp.BugTrackerApp.views;

import com.bugTrackerApp.BugTrackerApp.data.entity.Project;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.github.appreciated.card.Card;
import com.github.appreciated.card.action.ActionButton;
import com.github.appreciated.card.action.Actions;
import com.github.appreciated.card.content.Item;
import com.github.appreciated.card.label.PrimaryLabel;
import com.github.appreciated.card.label.SecondaryLabel;
import com.github.appreciated.card.label.TitleLabel;
import com.vaadin.flow.component.formlayout.FormLayout;
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

    // Components: form, grid
    FormLayout cardResponsiveFormLayout = new FormLayout();


    public HomeView(TicketSystemService TSService) {
        this.TSService = TSService;
//        Card card = new Card(
//                new TitleLabel("This is a card"),
//                new PrimaryLabel("Some primary text"),
//                new SecondaryLabel("Some secondary text"),
//                new Item("Item title", "Item description"),
//                new Actions(
//                        new ActionButton("Action 1", event -> {/* Handle Action*/}),
//                        new ActionButton("Action 2", event -> {/* Handle Action*/})
//                )
//        );
//        Project project = TSService.findProjectByName("Animals App");
//        Card projectCard = new Card(
//                new TitleLabel(project.getName()),
//                new PrimaryLabel(project.getDescription()),
//                new SecondaryLabel(project.getProjectStatus().getName())
//        );
//
//        // will store all projects in a horizontalLayout
//        HorizontalLayout projectCards = new HorizontalLayout();

        // get all projects
        // loop through all projects and set Labels
//        for(Project p : projectList) {
//            projectCards.add(new Card(
//                    new TitleLabel(p.getName()),
//                    new PrimaryLabel("Project Description:"),
//                    new SecondaryLabel(p.getDescription()),
//                    new Item("Project Status:", p.getProjectStatus().getName())
//
//            ));
//        }

//        projectCards.setJustifyContentMode(JustifyContentMode.AROUND);
//        HorizontalLayout projectCards2 = new HorizontalLayout(card, projectCard);
//        projectCards2.setJustifyContentMode(JustifyContentMode.CENTER);

//        add(projectCards, projectCards2);

        // try in a form with a max column size

        createProjectCards();
        configureCardResponsiveFormLayout();

        add(cardResponsiveFormLayout);
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
                    new Item("Project Status:", p.getProjectStatus().getName())

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

}
