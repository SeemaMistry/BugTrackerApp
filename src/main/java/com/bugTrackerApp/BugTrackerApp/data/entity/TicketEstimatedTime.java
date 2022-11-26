package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticketEstimatedTimeId"))
})
public class TicketEstimatedTime extends AbstractEntity{
    @NotNull
    @Column(unique = true)
    private int estimatedTime;

    @NotBlank
    private String description;

    @OneToMany(mappedBy = "ticketEstimatedTime")
    @Nullable
    private List<Ticket> tickets = new ArrayList<>();

    public TicketEstimatedTime(int estimatedTime, String description) {
        this.estimatedTime = estimatedTime;
        this.description = description;
    }
}


