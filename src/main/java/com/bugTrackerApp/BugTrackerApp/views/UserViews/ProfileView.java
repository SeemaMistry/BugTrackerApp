package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Profile | Bug Tracker")
@Route(value="profile")
public class ProfileView extends VerticalLayout {
    public ProfileView() {
        H1 welcome = new H1("My Profile");
        add(welcome);
    }
}
