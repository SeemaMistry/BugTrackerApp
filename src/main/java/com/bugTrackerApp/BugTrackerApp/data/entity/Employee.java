package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "emplpoyee_id"))
})
public class Employee extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull
    @JsonIgnoreProperties({"employees", "departments", "securityClearances"})
    private Company company;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "security_clearance_id")
    private SecurityClearance securityClearance;

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email = "";

    @OneToMany(mappedBy = "ticketReporter")
    @Nullable
    private List<Ticket> reportedTickets = new LinkedList<>();

    @CreationTimestamp
    private Timestamp createdDate;

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

    // TODO: things i needed to add when extracting fields to Usesr entity



    public Employee(Company company, SecurityClearance securityClearance, String firstName, String lastName, String email) {
        this.company = company;
        this.securityClearance = securityClearance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    //TODO: things ive removed when i extracted some fields into Usesr entity
    //TODO: might delete later
//    public Employee(Company company, String firstName, String lastName, String email, String username, String password) {
//        this.company = company;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.username = username;
//        this.password = password;
//    }

//    @ManyToOne
//    @JoinColumn(name = "account_status_id")
//    @NotNull
//    @JsonIgnoreProperties({"accountStatuses"})
//    private AccountStatus accountStatus;

//    @Column(unique = true)
//    @NotEmpty
//    private String username = "";
//
//    @Nullable
//    private String password = "";


}
