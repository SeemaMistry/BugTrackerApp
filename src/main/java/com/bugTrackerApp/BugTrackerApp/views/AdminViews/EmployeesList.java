package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Employees List | Bug Tracker")
@Route(value="employees")
public class EmployeesList extends VerticalLayout {
    public EmployeesList() {
        H1 welcome = new H1("See your list of employees");
        add(welcome);
    }
}
