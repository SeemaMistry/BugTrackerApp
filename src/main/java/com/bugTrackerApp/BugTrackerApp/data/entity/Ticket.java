package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticketId"))
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
    @JoinColumn(name = "projectId")
    @NotNull
    @JsonIgnoreProperties({"projectStatus", "employeesAssignedToTicket", "tickets", "creatorEmployee"})
    private Project project;

    @ManyToOne
    @JoinColumn(name = "reporterEmployeeId")
    @NotNull
    @JsonIgnoreProperties({"projectsAssignedToEmployee", "assignedTickets", "accountStatus", "reportedTickets", "company"})
    private Employee ticketReporter;

    @ManyToOne
    @JoinColumn(name = "ticketPriorityId")
    @NotNull
    @JsonIgnoreProperties({"tickets"})
    private TicketPriority ticketPriority;

    @ManyToOne
    @JoinColumn(name = "ticketEstimatedTimeId")
    @NotNull
    private TicketEstimatedTime ticketEstimatedTime;

    @ManyToOne
    @JoinColumn(name = "ticketTypeId")
    @NotNull
    @JsonIgnoreProperties({"description", "tickets"})
    private TicketType ticketType;

    @ManyToOne
    @JoinColumn(name = "statusId")
    @NotNull
    @JsonIgnoreProperties({"description", "tickets",  "projects"})
    private Status ticketStatus;

    @ManyToMany
    @JoinTable(
            name =  "ticketsAssignedToEmployees",
            joinColumns = { @JoinColumn(name = "ticketId") },
            inverseJoinColumns = { @JoinColumn(name = "employeeId") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Employee> employeesAssignedToTicket = new ArrayList<>();

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
                  List<Employee> employeesAssignedToTicket) {
        this.subject = subject;
        this.dueDate = dueDate;
        this.project = project;
        this.ticketReporter = ticketReporter;
        this.ticketPriority = ticketPriority;
        this.ticketEstimatedTime = ticketEstimatedTime;
        this.ticketType = ticketType;
        this.ticketStatus = ticketStatus;
        this.employeesAssignedToTicket = employeesAssignedToTicket;
    }

    public LocalDate getFormattedCreatedDate() {
        return this.createdDate.toLocalDateTime().toLocalDate();
    }
}
