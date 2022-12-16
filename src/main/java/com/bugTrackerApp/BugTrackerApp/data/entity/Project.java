package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
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

    @ManyToMany
    @JoinTable(
            name = "projectsAssignedToEmployees",
            joinColumns = { @JoinColumn(name = "projectId")},
            inverseJoinColumns = { @JoinColumn(name = "employeeId")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Employee> employeesAssignedToProject = new ArrayList<>();

    public Project(String name, String description, Employee creatorEmployee, Status projectStatus) {
        this.name = name;
        this.description = description;
        this.creatorEmployee = creatorEmployee;
        this.projectStatus = projectStatus;
    }

    public LocalDate getFormattedCreatedDate() {
        return this.createdDate.toLocalDateTime().toLocalDate();
    }
}
