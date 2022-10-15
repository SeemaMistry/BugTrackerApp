package com.bugTrackerApp.BugTrackerApp.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "user_id"))
})
// user entity containing: username, password, role, account status
// in a 1:1 relationship with employee
public class User extends AbstractEntity{
    // OWNED FIELDS
    @Column(unique = true)
   // @NotNull
    private String username;
    private String passwordSalt;
    private String passwordHash;

    //@NotNull
    private Role role;

//    @ManyToOne
//    @JoinColumn(name = "account_status_id")
//    @NotNull
//    @JsonIgnoreProperties({"accountStatuses"})
//    private AccountStatus accountStatus;

    public User(String username, String password, Role role) {
        this.username = username;
        this.role = role;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
    }

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }


}
