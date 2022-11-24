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
        @AttributeOverride(name = "id", column = @Column(name = "statusId"))
})
public class Status extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    @OneToMany(mappedBy = "ticketStatus")
    @Nullable
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "projectStatus")
    @Nullable
    private List<Project> projects = new ArrayList<>();

    public Status(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
