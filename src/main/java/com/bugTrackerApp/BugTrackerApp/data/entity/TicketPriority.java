package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticket_priority_id"))
})
public class TicketPriority extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private String name;

}

