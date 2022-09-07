package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Data
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

    @NotBlank
    private Date dueDate;

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
    private Set<Employee> employeeList;


}