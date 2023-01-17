package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "projectId"))
})
public class Project extends AbstractEntity{
    @NotBlank
//    @Column(unique = true)
    private String name;

    @NotNull
    @Column(unique = true)
    private String referenceValue;

    @NotBlank
    private String description;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "creatorEmployeeId")
    @NotNull
    private Employee creatorEmployee;

    @OneToMany(mappedBy = "project")
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

    public void setReferenceValue(){
        Random rand = new Random();
        this.referenceValue =
                getName().replaceAll(" ", "").toLowerCase() +

                String.valueOf(rand.nextInt(1000));
    }

    public LocalDate getFormattedCreatedDate() {
        return this.createdDate.toLocalDateTime().toLocalDate();
    }
}
