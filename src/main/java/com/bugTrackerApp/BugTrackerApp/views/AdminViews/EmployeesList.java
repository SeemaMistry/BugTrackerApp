package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.Forms.EmployeeForm;
import com.bugTrackerApp.BugTrackerApp.views.Forms.RegisterNewEmployeeForm;
import com.bugTrackerApp.BugTrackerApp.views.MainLayout;
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
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;

/*
    Employee list displays a list of all the emplpyees with a form to edit/add employees (by admin only)
*/
@PageTitle("Employees List | Bug Tracker")
@Route(value="employees", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EmployeesList extends VerticalLayout {
    // Services
    UserRelationsService URService;

    // instantiate components and form
    Grid<Employee> employeeGrid = new Grid<>(Employee.class);
    TextField filterText = new TextField();
    EmployeeForm employeeForm;
    RegisterNewEmployeeForm registerNewEmployeeForm;

    public EmployeesList(UserRelationsService URService) {
        this.URService = URService;
        addClassName("employee-list-view");
        H1 welcome = new H1("See your list of employees");

        // configure grid and form
        setSizeFull();
        configureGrid();
        configureForm();

        // render components and update list
        add( welcome, getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    // configure toolbar
    private Component getToolbar() {
        // configure filterText to search for employee by name and update the list
        filterText.setPlaceholder("Search by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        // configure button to add a new employee
        Button addEmployeeBtn = new Button("Add new employee");
        addEmployeeBtn.addClickListener(e -> addEmployee());

        // add toolbar components in a horizontal layout
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeBtn);

        return toolbar;
    }

    // configure employee grid
    private void configureGrid() {
        // configure columns
        employeeGrid.addClassName("employee-grid");
        employeeGrid.setSizeFull();
        employeeGrid.setColumns("firstName", "lastName",  "email");
        employeeGrid.addColumn(e -> e.getCompany().getName()).setHeader("Company").setSortable(true);
        employeeGrid.addColumn(e -> e.getSecurityClearance().getSecurityTitle()).setHeader("Security Clearance");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // single select employee populates employee form
        employeeGrid.asSingleSelect().addValueChangeListener(e -> editEmployee(e.getValue()));
    }
    // return grid and form in a horizontal layout
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(employeeGrid, employeeForm,registerNewEmployeeForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    // configure Employee Form
    private void configureForm() {
        // instantiate employeeForm with Company and SecurityClearance data
        employeeForm = new EmployeeForm(
                URService.findAllCompanies(filterText.getValue()),
                URService.findAllSecurityClearances(),
                URService
        );

        // set size of form
        employeeForm.setWidth("25em");

        // add save, delete and close click events
        employeeForm.addListener(EmployeeForm.SaveEvent.class, this::saveEmployee);
        employeeForm.addListener(EmployeeForm.DeleteEvent.class, this::deleteEmployee);
        employeeForm.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());

        registerNewEmployeeForm = new RegisterNewEmployeeForm(
                URService.findAllCompanies(filterText.getValue()),
                URService.findAllSecurityClearances(),
                URService
        );

        // set size of form
        registerNewEmployeeForm.setWidth("25em");

        // add save, delete and close click events
        registerNewEmployeeForm.addListener(EmployeeForm.SaveEvent.class, this::saveEmployee);
        registerNewEmployeeForm.addListener(EmployeeForm.DeleteEvent.class, this::deleteEmployee);
        registerNewEmployeeForm.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());

    }

    // delete employee from database and update list
    private void deleteEmployee(EmployeeForm.DeleteEvent e) {
        URService.deleteEmployee(e.getEmployee());
        updateList();
        closeEditor();
    }

    // save employee to database and update list
    private void saveEmployee(EmployeeForm.SaveEvent e) {
        URService.saveEmployee(e.getEmployee());
        updateList();
        closeEditor();
    }

    // add a new employee
    private void addEmployee() {
        // clear the form and open the editor
        employeeGrid.asSingleSelect().clear();
        closeEditor();
        registerNewEmployeeForm.setVisible(true);
        registerNewEmployeeForm.setEmployee(new Employee());
        addClassName("editing");
    }


    // update list based on search results or display all employees
    private void updateList() {
//        employeeGrid.setItems(URService.findAllEmployees(filterText.getValue()));

        employeeGrid.setItems(URService.findAllEmployeesByCompany(filterText.getValue(), VaadinSession.getCurrent().getAttribute(Company.class).getId()));
    }

    // close editor when not in use
    private void closeEditor() {
        // clear editor and close it
        employeeForm.setEmployee(null);
        employeeForm.setVisible(false);
        registerNewEmployeeForm.setVisible(false);
        removeClassName("editing");
    }

    // edit the form or populate it with a selected employee from the grid
    private void editEmployee (Employee employee){
        // if no employee selected, closeEditor, else populate employee info into form
        if (employee == null) {
            closeEditor();
        } else {
            registerNewEmployeeForm.setVisible(false);
            employeeForm.setEmployee(employee);
            employeeForm.setVisible(true);
            addClassName("editing");
        }
    }

}
