package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "companyId")
    @Nullable
    private Company company;

    @ManyToOne
    @JoinColumn(name = "creatorEmployeeId")
    @NotNull
    private Employee creatorEmployee;

    @OneToMany(mappedBy = "project")
    @Nullable
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "statusId")
    @NotNull
    private Status projectStatus;

    @ManyToMany(mappedBy = "projectsAssignedToEmployee")
    @Fetch(FetchMode.SELECT)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Employee> employeesAssignedToProject = new ArrayList<>();

    // 1:M ETP mapped to composite key project

    public Project(String name, String description, Employee creatorEmployee, Status projectStatus) {
        this.name = name;
        this.description = description;
        this.creatorEmployee = creatorEmployee;
        this.projectStatus = projectStatus;
    }
}
