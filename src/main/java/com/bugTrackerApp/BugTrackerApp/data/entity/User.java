package com.bugTrackerApp.BugTrackerApp.data.entity;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
//@Data
@Getter
@Setter
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

    @OneToOne(mappedBy = "userAccountDetail", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    // change plain password into passwordHash
    public void changePassword(String password) {
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
    }


}
