package com.bugTrackerApp.BugTrackerApp;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@Theme(value = "BugTrackerApp") // allows reading of styles.css. Requires: SpringBootServletInitializer & AppShellConfigurator
@NpmPackage(value = "line-awesome", version = "1.3.0") // import line awesome, url specified in style.css
@SpringBootApplication
public class BugTrackerAppApplication extends SpringBootServletInitializer implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(BugTrackerAppApplication.class, args);
	}
}
