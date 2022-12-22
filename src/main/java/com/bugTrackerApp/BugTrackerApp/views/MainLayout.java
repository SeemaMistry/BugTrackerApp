package com.bugTrackerApp.BugTrackerApp.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

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

        // set highlight conditions

        // add router links to Drawer navigation 
    }
}
