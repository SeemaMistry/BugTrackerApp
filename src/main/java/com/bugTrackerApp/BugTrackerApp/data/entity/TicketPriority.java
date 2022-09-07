package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticket_priority_id"))
})
public class TicketPriority extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "ticketPriority")
    @NotNull
    private List<Ticket> tickets = new LinkedList<>();

}
