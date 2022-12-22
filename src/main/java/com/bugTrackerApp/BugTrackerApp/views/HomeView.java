package com.bugTrackerApp.BugTrackerApp.views;

import com.bugTrackerApp.BugTrackerApp.data.entity.Company;
import com.bugTrackerApp.BugTrackerApp.data.service.UserRelationsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@PageTitle("Home | Bug Tracker")
@Route(value="", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {
    // TODO: test company search is working
    // initialize components
    Grid<Company> grid = new Grid<>(Company.class);
    TextField filterText = new TextField();
    UserRelationsService userRelationsService;

    public HomeView(UserRelationsService userRelationsService) {
        this.userRelationsService = userRelationsService;
        H1 welcome = new H1("Welcome to Bug Tracker");
        grid.setColumns("name");

        add(welcome,
                getToolbar(),
                grid);
        updateList();
    }

    private void updateList() {
        grid.setItems(userRelationsService.findAllCompanies(filterText.getValue()));
    }

    private Component getToolbar() {
        // apply filtertext button behaviours
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); // LAZY = will not hit database with every keystroke, wait for a while
        filterText.addValueChangeListener(e -> updateList());
        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}
