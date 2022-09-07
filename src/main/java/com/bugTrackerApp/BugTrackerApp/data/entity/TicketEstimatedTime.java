package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticket_estimated_time_id"))
})
public class TicketEstimatedTime extends AbstractEntity{
    @NotNull
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


