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
    private static int staticProjectCount = 100;
    private int referenceNumber; // store referenceNumber to persist throughout changes to project
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

    // set referenceNumber and increment staticProjectCount
    public void setReferenceNumber(){
        this.referenceNumber = staticProjectCount;
        staticProjectCount++;
    }

    private int getReferenceNumber(){
        return this.referenceNumber;
    }
    public void setReferenceValue(){
        // if reference is not already set, set it to current staticProjectCount
        if (referenceNumber == 0) {setReferenceNumber();}

        // set referenceValue with stored referenceNumber
        // (i.e. referenceNumber will NOT change when project name changes)
        this.referenceValue =
                getName().replaceAll(" ", "").toLowerCase() +
                String.valueOf(this.getReferenceNumber());
    }

    public LocalDate getFormattedCreatedDate() {
        return this.createdDate.toLocalDateTime().toLocalDate();
    }
}
