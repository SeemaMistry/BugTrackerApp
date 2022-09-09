package com.bugTrackerApp.BugTrackerApp.views.LoginViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login | Bug Tracker")
@Route(value="login")
public class Login extends VerticalLayout {

    public Login() {
        H1 welcome = new H1("Login in to your account");
        add(welcome);
    }
}
