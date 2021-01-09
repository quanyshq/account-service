package com.demo.useraccountservice.useraccount;


import com.demo.useraccountservice.common.EntityAlreadyExists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class UserAccountServiceTest {
    @Autowired
    private UserAccountService userAccountService;

    @Test
    void findByEmailWhenEmailIsRegistered() {
        var acc = userAccountService.findByEmail("marry@example.com");
        assertThat(acc).isNotNull();
        assertThat(acc.getId()).isNotNull();
        assertThat(acc.getEmail()).isEqualTo("marry@example.com");
    }

    @Test
    void findByEmailWhenEmailIsNotRegistered() {
        var acc = userAccountService.findByEmail("marry2@example.com");
        assertThat(acc).isNull();
    }

    @Test
    void registerWithPasswordWhenEmailIsNew() {
        var acc = new UserAccount("bob@example.com");
        userAccountService.registerWithPassword(acc, "123");
        assertThat(acc.getId()).isNotNull();
    }

    @Test
    void registerWithPasswordWhenEmailIsAlreadyRegistered() {
        var acc = new UserAccount("marry@example.com");
        assertThatThrownBy(() -> userAccountService.registerWithPassword(acc, "123"))
                .isInstanceOf(EntityAlreadyExists.class);
    }

    @Test
    void registerWithPasswordWhenRegisteredEmailHasDifferentCase() {
        var acc = new UserAccount("MARRY@EXAMPLE.COM");
        assertThatThrownBy(() -> userAccountService.registerWithPassword(acc, "123"))
                .isInstanceOf(EntityAlreadyExists.class);
    }

    @Test
    void registerUWithPasswordWhenRegisteredEmailIsSurroundedBySpaces() {
        var acc = new UserAccount("  marry@example.com  ");
        assertThatThrownBy(() -> userAccountService.registerWithPassword(acc, "123"))
                .isInstanceOf(EntityAlreadyExists.class);
    }

    @Test
    void registerWithPasswordWhenEmailIsBlank() {
        var acc = new UserAccount(" ");
        assertThatThrownBy(() -> userAccountService.registerWithPassword(acc, "123"))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void registerWithPasswordWhenPasswordIsEmpty() {
        var acc = new UserAccount("bob@example.com");
        assertThatThrownBy(() -> userAccountService.registerWithPassword(acc, ""))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
