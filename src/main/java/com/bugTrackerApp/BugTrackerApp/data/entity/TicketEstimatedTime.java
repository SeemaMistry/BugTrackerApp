package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticket_estimated_time_id"))
})
public class TicketEstimatedTime extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private int estimatedTime;

    @NotBlank
    private String description;

    @OneToMany(mappedBy = "ticketEstimatedTime")
    @Nullable
    private List<Ticket> tickets = new LinkedList<>();

    public TicketEstimatedTime(int estimatedTime, String description) {
        this.estimatedTime = estimatedTime;
        this.description = description;
    }
}


