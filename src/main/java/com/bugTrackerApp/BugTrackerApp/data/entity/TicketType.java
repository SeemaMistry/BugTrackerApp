package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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

