package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "account_status_id"))
})
public class AccountStatus extends AbstractEntity {
    @NotEmpty
    private String name = "";
}
