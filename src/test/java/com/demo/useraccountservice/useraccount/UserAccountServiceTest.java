package com.demo.useraccountservice.useraccount;


import com.demo.useraccountservice.common.EntityAlreadyExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserAccountServiceTest {
    private UserAccountRepository repo;
    private UserAccountService srv;

    @BeforeEach
    void setUp() {
        repo = mock(UserAccountRepository.class);
        srv = new UserAccountServiceRepoImpl(repo, new BCryptPasswordEncoder(12));
    }

    @Test
    void registerWithPasswordWhenEmailIsNew() {
        when(repo.existsByEmailIgnoreCase("bob@example.com")).thenReturn(false);
        var acc = new UserAccount("bob@example.com");
        srv.registerWithPassword(acc, "123");
        verify(repo).existsByEmailIgnoreCase("bob@example.com");
        verify(repo, times(1)).save(acc);
    }

    @Test
    void registerWithPasswordWhenEmailIsAlreadyRegistered() {
        when(repo.existsByEmailIgnoreCase("marry@example.com")).thenReturn(true);
        var acc = new UserAccount("marry@example.com");
        assertThatThrownBy(() -> srv.registerWithPassword(acc, "123"))
                .isInstanceOf(EntityAlreadyExists.class);
        verify(repo, times(0)).save(any());
    }

    @Test
    void registerWithPasswordWhenPasswordIsEmpty() {
        var acc = new UserAccount("bob@example.com");
        assertThatThrownBy(() -> srv.registerWithPassword(acc, ""))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, times(0)).save(any());
    }
}
