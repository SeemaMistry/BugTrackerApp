package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
    // instantiate components, services and form
    Grid<Employee> employeeGrid = new Grid<>(Employee.class);
    TextField filterText = new TextField();
    UserRelationsService URService;
    EmployeeForm employeeForm;

    public EmployeesList(UserRelationsService URService) {
        this.URService = URService;

        // configure grid and form
        setSizeFull();
        configureGrid();


        employeeForm = new EmployeeForm(URService.findAllCompanies(filterText.getValue()), URService.findAllAccountStatuses());
        employeeForm.setWidth("25em");

        H1 welcome = new H1("See your list of employees");

        add(welcome, getContent());
        updateList();
    }

    /* Return grid and form in a horizontal layout */
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(employeeGrid, employeeForm);
        content.setSizeFull();
        return content;
    }

    private void updateList() {
        employeeGrid.setItems(URService.findAllEmployees());
    }

    private void configureGrid() {
       employeeGrid.setSizeFull();
       employeeGrid.setColumns("firstName", "lastName",  "email");
       employeeGrid.addColumn(e -> e.getCompany().getName()).setHeader("Company");
       employeeGrid.addColumn(e -> e.getAccountStatus().getName()).setHeader("Account Status");
       employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
