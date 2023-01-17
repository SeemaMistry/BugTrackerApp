package com.bugTrackerApp.BugTrackerApp.views.LoginViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Register | Bug Tracker")
@Route(value="register")
@PermitAll
public class RegisterView extends VerticalLayout {
    public RegisterView() {
        H1 welcome = new H1("Register a new account");
        add(welcome);
    }

    /* ------------------- CONFIGURATIONS -------------------
     * */


    /* ------------------- GET COMPONENTS -------------------
     * */

    /* ------------------- FORM MANIPULATIONS -------------------
     * add new account, save, delete, clear, open and close
     * */
}
