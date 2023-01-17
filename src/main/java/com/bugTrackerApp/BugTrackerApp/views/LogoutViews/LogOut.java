package com.bugTrackerApp.BugTrackerApp.views.LogoutViews;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.PermitAll;

@PageTitle("Logout | Bug Tracker")
@Route(value="logout")
@PermitAll
public class LogOut extends VerticalLayout {

    public LogOut() {
        // return page to login page
        UI.getCurrent().getPage().setLocation("login");

        // invalidate and close current session
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();


        H1 welcome = new H1("You have logged Out!");
        add(welcome);
    }
}
