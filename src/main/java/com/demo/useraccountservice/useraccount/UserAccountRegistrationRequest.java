package com.demo.useraccountservice.useraccount;


import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAccountRegistrationRequest {
    @NotNull
    private UserAccount userAccount;
    @NotEmpty
    @Size(min = 3, max = 255)
    private String password;


    public UserAccountRegistrationRequest() {
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Transient // disable JSON serialization
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

