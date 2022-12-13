package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "employeeId"))
})
public class Employee extends AbstractEntity{

//    @OneToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "userId")
//    private User userAccountDetail;

    @ManyToOne
    @JoinColumn(name = "companyId")
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

    @Transient
    private String fullName;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email = "";

    @OneToMany(mappedBy = "ticketReporter")
    @Nullable
    private List<Ticket> reportedTickets = new ArrayList<>();

    @OneToMany(mappedBy = "creatorEmployee")
    @Nullable
    private List<Project> projectsCreated = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdDate;

    @ManyToMany(mappedBy = "employeesAssignedToTicket")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Ticket> ticketsAssignedToEmployees = new ArrayList<>();

    @ManyToMany(mappedBy = "employeesAssignedToProject")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Project> projectsAssignedToEmployee = new ArrayList<>();

    public Employee(Company company, SecurityClearance securityClearance, String firstName, String lastName, String email) {
        this.company = company;
        this.securityClearance = securityClearance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // get custom fullname
    public String getFullName() {
        return (firstName + " " + lastName);
    }

}
