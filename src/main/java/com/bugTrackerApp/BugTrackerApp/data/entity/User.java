package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "userId"))
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

    @NotNull
    private Role role;

    @ManyToOne
    @JoinColumn(name = "accountStatusId")
//    @NotNull
    private AccountStatus accountStatus;

    @OneToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

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
