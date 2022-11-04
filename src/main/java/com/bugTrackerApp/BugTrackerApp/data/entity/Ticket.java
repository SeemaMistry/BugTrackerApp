package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticket_id"))
})
public class Ticket extends AbstractEntity{

    @NotNull
    private String subject;

    @CreationTimestamp
    private Timestamp  createdDate;

    @UpdateTimestamp
    private Timestamp updatedDate;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @NotNull
    @JsonIgnoreProperties({"projectStatus", "employeeList", "tickets", "creator_employee"})
    private Project project;

    @ManyToOne
    @JoinColumn(name = "reporter_employee_id")
    @NotNull
    @JsonIgnoreProperties({"invitedProjects", "assignedTickets", "accountStatus", "reportedTickets", "company"})
    private Employee ticketReporter;

    @ManyToOne
    @JoinColumn(name = "ticket_priority_id")
    @NotNull
    @JsonIgnoreProperties({"tickets"})
    private TicketPriority ticketPriority;

    @ManyToOne
    @JoinColumn(name = "ticket_estimated_time_id")
    @NotNull
    private TicketEstimatedTime ticketEstimatedTime;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    @NotNull
    @JsonIgnoreProperties({"description", "tickets"})
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "status_id")
    @NotNull
    @JsonIgnoreProperties({"description", "tickets",  "projects"})
    private Status ticketStatus;

    @ManyToMany(mappedBy = "assignedTickets")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Employee> employeeList;

    public Ticket(String subject,
                  LocalDate dueDate,
                  Project project,
                  Employee ticketReporter,
                  TicketPriority ticketPriority,
                  TicketEstimatedTime ticketEstimatedTime,
                  TicketType ticketType,
                  Status ticketStatus) {
        this.subject = subject;
        this.dueDate = dueDate;
        this.project = project;
        this.ticketReporter = ticketReporter;
        this.ticketPriority = ticketPriority;
        this.ticketEstimatedTime = ticketEstimatedTime;
        this.ticketType = ticketType;
        this.ticketStatus = ticketStatus;
    }

    public Ticket(String subject,
                  LocalDate dueDate,
                  Project project,
                  Employee ticketReporter,
                  TicketPriority ticketPriority,
                  TicketEstimatedTime ticketEstimatedTime,
                  TicketType ticketType,
                  Status ticketStatus,
                  Set<Employee> employeeList) {
        this.subject = subject;
        this.dueDate = dueDate;
        this.project = project;
        this.ticketReporter = ticketReporter;
        this.ticketPriority = ticketPriority;
        this.ticketEstimatedTime = ticketEstimatedTime;
        this.ticketType = ticketType;
        this.ticketStatus = ticketStatus;
        this.employeeList = employeeList;
    }

}
