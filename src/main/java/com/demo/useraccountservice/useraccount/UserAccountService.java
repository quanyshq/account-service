package com.demo.useraccountservice.useraccount;

import com.demo.useraccountservice.common.EntityAlreadyExists;

import javax.validation.ConstraintViolationException;

public interface UserAccountService {
    /**
     * @return null if the given email is not registered
     */
    UserAccount findByEmail(String email);

    /**
     * @throws IllegalArgumentException     if the given UserAccount object is null or password is empty
     * @throws EntityAlreadyExists          if the given UserAccount object has an email that has already been registered
     * @throws ConstraintViolationException if the given UserAccount object has validation errors
     */
    void registerWithPassword(UserAccount userAccount, String password)
            throws IllegalArgumentException, EntityAlreadyExists, ConstraintViolationException;
}
