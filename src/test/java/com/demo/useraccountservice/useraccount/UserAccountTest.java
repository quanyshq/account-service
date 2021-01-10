package com.demo.useraccountservice.useraccount;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAccountTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        validatorFactory.close();
    }

    @Test
    void constructWhenEmailSurroundedBySpaces() {
        var acc = new UserAccount(1L, "  bob@example.com  ");
        assertThat(acc.getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    void setEmailWhenEmailSurroundedBySpaces() {
        var acc = new UserAccount();
        acc.setEmail("  bob@example.com  ");
        assertThat(acc.getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    void validateWhenEmailIsValid() {
        var acc = new UserAccount(1L, "bob@example.com");
        var violations = validator.validate(acc);
        assertThat(violations).isEmpty();
    }

    @Test
    void validateWhenEmailIsNull() {
        var acc = new UserAccount(1L, null);
        var violations = validator.validate(acc);
        assertThat(violations).extracting(v -> v.getPropertyPath().toString()).contains("email");
    }

    @Test
    void validateWhenEmailIsBlank() {
        var acc = new UserAccount(1L, "   ");
        var violations = validator.validate(acc);
        assertThat(violations).extracting(v -> v.getPropertyPath().toString()).contains("email");
    }


    @Test
    void validateWhenEmailIsInvalid() {
        var acc = new UserAccount(1L, "email");
        var violations = validator.validate(acc);
        assertThat(violations).extracting(v -> v.getPropertyPath().toString()).contains("email");
    }
}
