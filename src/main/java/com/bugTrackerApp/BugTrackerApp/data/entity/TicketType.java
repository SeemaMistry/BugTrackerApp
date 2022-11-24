package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ticketTypeId"))
})
public class TicketType extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "ticketType")
    @Nullable
    private List<Ticket> tickets = new ArrayList<>();

    public TicketType(String name) {
        this.name = name;
    }
}

