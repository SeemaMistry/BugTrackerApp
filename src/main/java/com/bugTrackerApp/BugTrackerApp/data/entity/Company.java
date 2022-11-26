package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "companyId"))
})
public class Company extends AbstractEntity{

    @NotBlank
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Department> departments = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<SecurityClearance> securityClearances = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Project> projects = new ArrayList<>();

    public Company(String name)  {
        this.name = name;
    }

}
