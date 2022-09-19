package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

/* Employee list displays a list of all the emplpyees with a form to edit/add employees (by admin only)
TODO: Create grid and populate with employees from DB
TODO: Instantiate EmployeeForm
TODO: populate EmployeeForm with selected employee from the grid
TODO: Set actions: set form invisible when screen size is small and when no employee selected
TODO: Make filterText search bar work (will require work in repos with @Queries)
*/
@PageTitle("Employees List | Bug Tracker")
@Route(value="employees")
@RolesAllowed("ADMIN")
public class EmployeesList extends VerticalLayout {
    public EmployeesList() {
        H1 welcome = new H1("See your list of employees");
        add(welcome);
    }
}
