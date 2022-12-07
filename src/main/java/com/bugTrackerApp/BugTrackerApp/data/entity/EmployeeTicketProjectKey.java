package com.bugTrackerApp.BugTrackerApp.data.entity;

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
    @Column(name = "employee")
    UUID employeeId;

    // ticket column
    @Column(name = "ticket")
    UUID ticketId;

    // project column
    @Column(name = "project")
    UUID projectId;
}
