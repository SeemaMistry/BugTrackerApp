package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "status_id"))
})
public class Status extends AbstractEntity{
    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;
}
