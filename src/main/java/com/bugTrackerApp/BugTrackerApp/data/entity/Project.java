package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "projectId"))
})
public class Project extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @ManyToOne
    @JoinColumn(name = "creatorEmployeeId")
    @NotNull
    // TODO: add json ignore properties
    @JsonIgnoreProperties()
    private Employee creatorEmployee;

    @OneToMany(mappedBy = "project")
    @Nullable
    private List<Ticket> tickets = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "statusId")
    @NotNull
    private Status projectStatus;

    @ManyToMany(mappedBy = "invitedProjects")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Employee> employeeList;

    public Project(String name, String description, Employee creatorEmployee, Status projectStatus) {
        this.name = name;
        this.description = description;
        this.creatorEmployee = creatorEmployee;
        this.projectStatus = projectStatus;
    }
}
