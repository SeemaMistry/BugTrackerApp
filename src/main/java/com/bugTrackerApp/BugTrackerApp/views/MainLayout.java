package com.bugTrackerApp.BugTrackerApp.views;

import com.bugTrackerApp.BugTrackerApp.views.AdminViews.EmployeesList;
import com.bugTrackerApp.BugTrackerApp.views.UserViews.ProfileView;
import com.bugTrackerApp.BugTrackerApp.views.UserViews.ProjectsList;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    public MainLayout() {
        // Header and side Drawer navigation's
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        // create Logo (just an H1 tag)
        H1 logo = new H1("Ticket Tracker");
        logo.addClassNames("text-l", "m-m");

        // create Horizontal layout with a Drawer and logo
        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        // add styling
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        // add to nav bar at the top of screen
        addToNavbar(header);
    }

    private void createDrawer() {
        // create router links
        RouterLink employeeLink = new RouterLink("Employees", EmployeesList.class);
        RouterLink projectLink = new RouterLink("Projects", ProjectsList.class);
        RouterLink profileLink = new RouterLink("My Profile", ProfileView.class);
        RouterLink homeLink = new RouterLink("Homepage", HomeView.class);

        // set highlight conditions
        homeLink.setHighlightCondition(HighlightConditions.sameLocation());

        // add router links to Drawer navigation
        addToDrawer(new VerticalLayout(
                homeLink,
                employeeLink,
                projectLink,
                profileLink
        ));
    }
}
