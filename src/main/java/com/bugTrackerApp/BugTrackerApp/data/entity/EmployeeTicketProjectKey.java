package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeTicketProjectKey implements Serializable {

    // employee column
    @Column(name = "employeeId")
    @JsonIgnoreProperties({"userAccountDetail", "company", "securityClearance", "firstName", "lastName", "email", "reportedTickets", "projectsCreated", "projectsAssignedToEmployee"})
    UUID employeeId;

    // ticket column
    @Column(name = "ticketId")
    @JsonIgnoreProperties({"subject", "createdDate", "updatedDate", "dueDate", "project", "ticketReporter", "ticketPriority", "ticketEstimatedTime", "ticketType", "employeesAssignedToTicket", "assignedTickets"})
    UUID ticketId;

    // project column
    @Column(name = "projectId")
    @JsonIgnoreProperties({"name", "description", "createdDate", "updateDate", "company", "creatorEmployee", "tickets", "projectStatus", "employeesAssignedToProject"})
    UUID projectId;
}
