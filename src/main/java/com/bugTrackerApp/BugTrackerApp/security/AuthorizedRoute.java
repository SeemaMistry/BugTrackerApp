package com.bugTrackerApp.BugTrackerApp.security;

import com.vaadin.flow.component.Component;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizedRoute {
    String path;
    String name;
    Class<? extends Component> view;
}
