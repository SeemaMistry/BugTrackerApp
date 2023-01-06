package com.bugTrackerApp.BugTrackerApp.views;

import com.bugTrackerApp.BugTrackerApp.data.entity.User;
import com.bugTrackerApp.BugTrackerApp.data.service.AuthService;
import com.bugTrackerApp.BugTrackerApp.data.service.AuthorizedRoute;
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
import com.vaadin.flow.server.VaadinSession;

import java.util.stream.Collectors;

public class MainLayout extends AppLayout {
    AuthService authService;
    public MainLayout(AuthService authService) {
        this.authService = authService;
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
        // store routes in a vertical layout
        VerticalLayout drawerLinks = new VerticalLayout();

        // retrieve current user
        User user = VaadinSession.getCurrent().getAttribute(User.class);

        // for each stored AuthorizedRoute, create new RouterLinks
        for(AuthorizedRoute r : authService.getAuthorizedRoutes(user.getRole())){
            drawerLinks.add(new RouterLink(r.getName(), r.getView()));
        }

        // add links to drawer
        addToDrawer(drawerLinks);

    }
}
