package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "account_status_id"))
})
public class AccountStatus extends AbstractEntity {
    @NotBlank
    private String name;

//    @OneToMany(mappedBy = "accountStatus")
//    @Nullable
//    private List<Employee> accountStatuses = new LinkedList<>();

    public AccountStatus(String name) {
        this.name = name;
    }
}
