package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "emplpoyee_id"))
})
public class Employee extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull
    @JsonIgnoreProperties({"employees", "departments", "securityClearances"})
    private Company company;

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email = "";

// TODO: Project to invited employees is a M:N (projects have many in.E, E. have many projects)
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    @Nullable
//    List<Project> projects = new LinkedList<>();

    @OneToMany(mappedBy = "ticketReporter")
    @Nullable
    private List<Ticket> reportedTickets = new LinkedList<>();

    @Column(unique = true)
    @NotEmpty
    private String username = "";

    @NotEmpty
    private String password = "";

    @CreationTimestamp
    private Timestamp createdDate;

    @ManyToOne
    @JoinColumn(name = "account_status_id")
    @NotNull
    @JsonIgnoreProperties({"accountStatuses"})
    private AccountStatus accountStatus;


    @ManyToMany
    @JoinTable(
            name = "ticket_assigned_employees",
            joinColumns = { @JoinColumn(name = "employee_id")},
            inverseJoinColumns = { @JoinColumn(name = "ticket_id")}
    )
    private Set<Ticket> assignedTickets;


    @ManyToMany
    @JoinTable(
            name = "invited_employees_to_project",
            joinColumns = { @JoinColumn(name = "employee_id")},
            inverseJoinColumns = { @JoinColumn(name = "project_id")}
    )
    private Set<Project> invitedProjects;


}
