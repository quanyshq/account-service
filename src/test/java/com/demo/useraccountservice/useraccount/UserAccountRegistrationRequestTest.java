package com.demo.useraccountservice.useraccount;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserAccountRegistrationRequestTest {
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
    void validateWhenItIsValid() {
        var acc = new UserAccount(1L, "bob@example.com");
        var req = new UserAccountRegistrationRequest();
        req.setUserAccount(acc);
        req.setPassword("123");
        var violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }

    @Test
    void validateWhenItIsEmpty() {
        var req = new UserAccountRegistrationRequest();
        var violations = validator.validate(req);
        assertThat(violations).extracting(v -> v.getPropertyPath().toString()).contains("userAccount", "password");
    }

    @Test
    void validateWhenUserAccountEmailIsInvalid() {
        var acc = new UserAccount(1L, "bob @ example.com");
        var req = new UserAccountRegistrationRequest();
        req.setUserAccount(acc);
        req.setPassword("123");
        var violations = validator.validate(req);
        assertThat(violations).extracting(v -> v.getPropertyPath().toString()).contains("userAccount.email");
    }
}