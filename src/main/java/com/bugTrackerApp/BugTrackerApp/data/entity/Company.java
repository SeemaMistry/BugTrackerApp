package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "company_id"))
})
public class Company extends AbstractEntity{

    @NotBlank
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Employee> employees = new LinkedList<>();

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Department> departments = new LinkedList<>();

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<SecurityClearance> securityClearances = new LinkedList<>();

    public Company(String name)  {
        this.name = name;
    }

}
