package com.bugTrackerApp.BugTrackerApp.views.AdminViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.entity.Employee;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.Forms.EmployeeForm;
import com.bugTrackerApp.BugTrackerApp.views.Forms.AddNewEmployeeForm;
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
    Employee list displays a list of all the employees with a form to edit/add employees (by admin only)
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
    AddNewEmployeeForm addNewEmployeeForm;
    Button addEmployeeBtn = new Button("Add new employee");

    public EmployeesList(UserRelationsService URService) {
        this.URService = URService;
        addClassName("employee-list-view");
        H1 welcome = new H1("See your list of employees");

        // configure grid and form
        setSizeFull();
        configureGrid();
        configureForm();
        configureToolbar();

        // render components and update list
        add( welcome, getToolbar(), getContent());
        updateList();
        closeEditor();
    }


    /* ------------------- CONFIGURATIONS -------------------
     * */

    // configure employee grid
    private void configureGrid() {
        // configure columns
        employeeGrid.addClassName("employee-grid");
        employeeGrid.setSizeFull();
        employeeGrid.setColumns("firstName", "lastName",  "email");
        employeeGrid.addColumn(e -> e.getUserAccountDetail().getUsername()).setHeader("Username").setSortable(true);
        employeeGrid.addColumn(e -> e.getSecurityClearance().getSecurityTitle()).setHeader("Security Clearance").setSortable(true);
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        // single select employee populates employee form
        employeeGrid.asSingleSelect().addValueChangeListener(e -> editEmployee(e.getValue()));
    }

    // configure Employee Form
    private void configureForm() {
        // instantiate employeeForm with Company and SecurityClearance data
        employeeForm = new EmployeeForm(
                URService.findAllSecurityClearances(),
                URService
        );

        // set size of form
        employeeForm.setWidth("25em");

        // add save, delete and close click events
        employeeForm.addListener(EmployeeForm.SaveEvent.class, this::saveEmployee);
        employeeForm.addListener(EmployeeForm.DeleteEvent.class, this::deleteEmployee);
        employeeForm.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());

        addNewEmployeeForm = new AddNewEmployeeForm(
                URService.findAllSecurityClearances(),
                URService
        );

        // set size of form
        addNewEmployeeForm.setWidth("25em");

        // add save, delete and close click events
        addNewEmployeeForm.addListener(EmployeeForm.SaveEvent.class, this::saveEmployee);
        addNewEmployeeForm.addListener(EmployeeForm.DeleteEvent.class, this::deleteEmployee);
        addNewEmployeeForm.addListener(EmployeeForm.CloseEvent.class, e -> closeEditor());

    }

    // configure toolbar components: filtertext, addNewEmployeeBtn
    private void configureToolbar(){
        // configure filterText to search for employee by name and update the list
        filterText.setPlaceholder("Search by name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        // configure button to add a new employee
        addEmployeeBtn.addClickListener(e -> addEmployee());
    }


    /* ------------------- GET COMPONENTS -------------------
     * */

    // return toolbar layout
    private Component getToolbar() {
        // add toolbar components in a horizontal layout
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addEmployeeBtn);
        return toolbar;
    }

    // return grid and forms in a horizontal layout
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(employeeGrid, employeeForm, addNewEmployeeForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }


    /* ------------------- UPDATE GRID EVENTS -------------------
     * */

    // update list based on search results or display all employees
    private void updateList() {
        employeeGrid.setItems(URService.findAllEmployeesByCompany(
                filterText.getValue(),
                VaadinSession.getCurrent().getAttribute(Company.class).getId()
        ));
    }


    /* ------------------- FORM MANIPULATIONS -------------------
     * add new employee, edit, save, delete, open and close
     * */

    // add a new employee
    private void addEmployee() {
        // clear the form and open the editor
        employeeGrid.asSingleSelect().clear();
        closeEditor();
        addNewEmployeeForm.setVisible(true);
        // set the company to the new Employee
        Employee newEmployee = new Employee();
        newEmployee.setCompany(VaadinSession.getCurrent().getAttribute(Company.class));
        addNewEmployeeForm.setEmployee(newEmployee);
        addClassName("editing");
    }

    // edit the form or populate it with a selected employee from the grid
    private void editEmployee (Employee employee){
        // if no employee selected, closeEditor, else populate employee info into form
        if (employee == null) {
            closeEditor();
        } else {
            addNewEmployeeForm.setVisible(false);
            employeeForm.setEmployee(employee);
            employeeForm.setVisible(true);
            addClassName("editing");
        }
    }

    // save employee to database and update list
    private void saveEmployee(EmployeeForm.SaveEvent e) {
        URService.saveEmployee(e.getEmployee());
        updateList();
        closeEditor();
    }

    // delete employee from database and update list
    private void deleteEmployee(EmployeeForm.DeleteEvent e) {
        URService.deleteEmployee(e.getEmployee());
        updateList();
        closeEditor();
    }

    // close editor when not in use
    private void closeEditor() {
        // clear editor and close it
        employeeForm.setEmployee(null);
        employeeForm.setVisible(false);
        addNewEmployeeForm.setVisible(false);
        removeClassName("editing");
    }

}
