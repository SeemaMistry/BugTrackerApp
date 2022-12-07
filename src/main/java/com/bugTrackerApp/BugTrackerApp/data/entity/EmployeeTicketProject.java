package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTicketProject {

    // embedded ETP key
    @EmbeddedId
    EmployeeTicketProjectKey employeeTicketProjectKeyId = new EmployeeTicketProjectKey();

    // M:1 employee mapping to key
    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employeeId")
    Employee employee;

    // M:1 ticket mapping to key
    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticketId")
    Ticket ticket;

    // M:1 project mapping to key
    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "projectId")
    Project project;

    // constructor WITHOUT key
    public EmployeeTicketProject(Employee employee, Ticket ticket, Project project) {
        this.employee = employee;
        this.ticket = ticket;
        this.project = project;
    }
}
