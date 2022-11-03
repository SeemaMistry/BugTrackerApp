package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

/*
    Employee list displays a list of all the emplpyees with a form to edit/add employees (by admin only)
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
        addClassName("employee-list-view");
        H1 welcome = new H1("See your list of employees");

        // configure grid and form
        setSizeFull();
        configureGrid();
        configureForm();

        add(welcome, getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Search by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEmployeeBtn = new Button("Add new employee");
        addEmployeeBtn.addClickListener(e -> addEmployee());
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeBtn);

        return toolbar;
    }

    private void addEmployee() {
        // clear the form and open the editor
        employeeGrid.asSingleSelect().clear();
        editEmployee(new Employee());
    }

    private void configureGrid() {
        employeeGrid.addClassName("employee-grid");
        employeeGrid.setSizeFull();
        employeeGrid.setColumns("firstName", "lastName",  "email");
        employeeGrid.addColumn(e -> e.getCompany().getName()).setHeader("Company");
        employeeGrid.addColumn(e -> e.getSecurityClearance().getSecurityTitle()).setHeader("Security Clearance");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // single select employee populates form
        employeeGrid.asSingleSelect().addValueChangeListener(e -> editEmployee(e.getValue()));
    }
    /* Return grid and form in a horizontal layout */
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(employeeGrid, employeeForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        employeeForm = new EmployeeForm(
                URService.findAllCompanies(filterText.getValue()),
                URService.findAllSecurityClearances()
        );
        employeeForm.setWidth("25em");
        employeeForm.addListener(EmployeeForm.SaveEvent.class, this::saveEmployee);
        employeeForm.addListener(EmployeeForm.DeleteEvent.class, this::deleteEmployee);
        employeeForm.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteEmployee(EmployeeForm.DeleteEvent e) {
        URService.deleteEmployee(e.getEmployee());
        updateList();
        closeEditor();
    }

    private void saveEmployee(EmployeeForm.SaveEvent e) {
        URService.saveEmployee(e.getEmployee());
        updateList();
        closeEditor();
    }

    private void updateList() {
        employeeGrid.setItems(URService.findAllEmployees(filterText.getValue()));
    }


    // Close editor when not in use
    private void closeEditor() {
        // clear editor and close it
        employeeForm.setEmployee(null);
        employeeForm.setVisible(false);
        removeClassName("editing");
    }

    // edit the form or populate it with a selected employee from the grid
    private void editEmployee (Employee employee){
        // if no employee selected, closeEditor, else populate employee info into form
        if (employee == null) {
            closeEditor();
        } else {
            employeeForm.setEmployee(employee);
            employeeForm.setVisible(true);
            addClassName("editing");
        }
    }

}
