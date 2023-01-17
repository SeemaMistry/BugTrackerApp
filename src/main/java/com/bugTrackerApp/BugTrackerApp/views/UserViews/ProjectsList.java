package com.bugTrackerApp.BugTrackerApp.views.UserViews;

import com.bugTrackerApp.BugTrackerApp.data.entity.*;
import com.bugTrackerApp.BugTrackerApp.data.service.TicketSystemService;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.bugTrackerApp.BugTrackerApp.views.Forms.ProjectForm;
import com.bugTrackerApp.BugTrackerApp.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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


@PageTitle("Projects | Bug Tracker")
@Route(value="projects", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class ProjectsList extends VerticalLayout {
    // Services and Components
    TicketSystemService TSService;
    UserRelationsService URService;

    Grid<Project> grid = new Grid<>(Project.class);
    TextField searchProjectByName = new TextField();
    ProjectForm projectForm;
    Button clearSearch = new Button("Clear Search");

    public ProjectsList(TicketSystemService TSService,  UserRelationsService URService) {
        this.TSService = TSService;
        this.URService = URService;
        H1 welcome = new H1("Projects Grid");

        //configure Grid
        setSizeFull();
        configureGrid();
        configureForm();

        // display components, update grids
        add(welcome, getToolbar(), getContent());
        updateList();

        // close project form as default
        closeEditor();

        // retrieve project session attribute and populate projectForm with Project object passed
        if (VaadinSession.getCurrent().getAttribute(Project.class) != null){
            this.editProject(VaadinSession.getCurrent().getAttribute(Project.class));
            // remove project selected object from set attribute
            VaadinSession.getCurrent().setAttribute(Project.class, null);
        } else {
            this.editProject(null);
        }
    }

    /* ------------------- CONFIGURATIONS -------------------
     * */

    // configure project grid
    private void configureGrid() {
        grid.setSizeFull();

        // configure columns
        grid.setColumns("name", "description");
        // create "View Tickets" button per row which redirects to project's ticketList page
        grid.addComponentColumn(project -> {
            Button btn = new Button("View Tickets",
                    event -> UI.getCurrent().navigate(TicketsList.class, project.getName()));
            return btn;
        });
        grid.addColumn(e -> e.getProjectStatus().getName()).setHeader("Status");
        grid.addColumn(e -> e.getCreatorEmployee().getFullName()).setHeader("Creator");
        grid.addColumn(e -> e.getFormattedCreatedDate()).setHeader("Created on");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        // single click to edit project
        grid.asSingleSelect().addValueChangeListener(e -> editProject(e.getValue()));

        // double click and be routed to tickets page
        grid.addItemDoubleClickListener(e ->
                UI.getCurrent().navigate(TicketsList.class, e.getItem().getName())
        );
    }

    // configure project Form with button click listeners
    private void configureForm() {
        // populate form ComboBoxes
        projectForm = new ProjectForm(
                URService.findAllEmployeesByCompany(null, VaadinSession.getCurrent().getAttribute(Company.class).getId()),
                TSService.findAllStatuses()
        );
        projectForm.setWidth("25em");

        // Use API calls for save, delete, close events
        projectForm.addListener(ProjectForm.SaveEvent.class, this::saveProject);
        projectForm.addListener(ProjectForm.DeleteEvent.class, this::deleteProject);
        projectForm.addListener(ProjectForm.CloseEvent.class, e -> closeEditor());
    }


    /* ------------------- GET COMPONENTS -------------------
     * */

    // configure toolbar: filters and add new Project button
    private HorizontalLayout getToolbar() {
        // add new project btn
        Button addNewProjectBtn = new Button("Add new project");
        addNewProjectBtn.addClickListener(e ->  addProject());

        // populate project grid with projects found via search value
        searchProjectByName.setPlaceholder("Search Project ...");
        searchProjectByName.setClearButtonVisible(true);
        searchProjectByName.setValueChangeMode(ValueChangeMode.LAZY);
        searchProjectByName.addValueChangeListener(e -> updateListByProjectNameSearch(e.getValue()));

        // configure clearSearch button to remove grid by search
        clearSearch.addClickListener(e -> {
            searchProjectByName.clear();
            updateList();
        });

        // dynamically render toolbar based on current user session role
        HorizontalLayout toolbar = new HorizontalLayout();
        if (VaadinSession.getCurrent().getAttribute(User.class).getRole() == Role.ADMIN) {
            toolbar.add(addNewProjectBtn);
        }
        toolbar.add(searchProjectByName, clearSearch);
        // display toolbar content in a clean line
        toolbar.setDefaultVerticalComponentAlignment(Alignment.END);
        return toolbar;
    }

    // return page content (grid and form) in Horizontal layout
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, projectForm);
        content.setSizeFull();
        return content;
    }


    /* ------------------- UPDATE GRID EVENTS -------------------
     * */

    // update project grid with all projects
    private void updateList() {
        grid.setItems(TSService.getAllProjectByCompany(VaadinSession.getCurrent().getAttribute(Company.class).getId()));
    }

    // update project grid with projects based on searchBYProjectName value
    private void updateListByProjectNameSearch(String searchName){
        grid.setItems(TSService.searchProjectByLikeName(searchName));
    }


    /* ------------------- FORM MANIPULATIONS -------------------
     * add new project, edit, save, delete, open and close
     * */

    // Add a new project
    private void addProject() {
        // clear form and open editor
        grid.asSingleSelect().clear();
        editProject(new Project());
    }

    // Edit existing project or create new project
    private void editProject(Project project) {
        // no project selected = close editor. Else populate form with project and open form
        if (project == null) {
            closeEditor();
        } else {
            projectForm.setProject(project);
            projectForm.setVisible(true);
        }
    }

    // Save project to database, update ticketGrid, and close form
    private void saveProject(ProjectForm.SaveEvent e) {
        // retrieve and set employees selected from MutliSelectComboBox to project
        e.getProject().setEmployeesAssignedToProject(projectForm.getEmployeesAssigned());
        // set company to project
        e.getProject().setCompany(VaadinSession.getCurrent().getAttribute(Company.class));
        TSService.saveProject(e.getProject());
        updateList();
        closeEditor();
    }

    // Delete project from database, update the ticketGrid, and close form
    private void deleteProject(ProjectForm.DeleteEvent e) {
        TSService.deleteProject(e.getProject());
        updateList();
        closeEditor();
    }

    // Close the editor
    private void closeEditor() {
        // clear editor and close it
        projectForm.setProject(null);
        projectForm.setVisible(false);
    }


}
