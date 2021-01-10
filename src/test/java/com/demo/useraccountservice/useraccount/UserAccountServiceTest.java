package com.demo.useraccountservice.useraccount;


import com.demo.useraccountservice.common.EntityAlreadyExists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

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

    @Test
    void authenticateWithPasswordWhenCredentialsAreValid() {
        var acc = new UserAccount(1L, "bob@example.com");
        //noinspection SpellCheckingInspection
        acc.setPasswordHash("$2y$12$LBD4CPY8MaRlu45zx548Fu6Qsw5W8j5Apna5DLtRoiS3N51DZ.rUG");

        when(repo.findAccountByEmailIgnoreCase("bob@example.com")).thenReturn(Optional.of(acc));
        var res = srv.authenticateWithPassword("bob@example.com", "123");
        assertThat(res).isEqualTo(acc);
    }


    @Test
    void authenticateWithPasswordWhenCredentialsAreNotValid() {
        var acc = new UserAccount(1L, "bob@example.com");
        //noinspection SpellCheckingInspection
        acc.setPasswordHash("$2y$12$LBD4CPY8MaRlu45zx548Fu6Qsw5W8j5Apna5DLtRoiS3N51DZ.rUG");

        when(repo.findAccountByEmailIgnoreCase("bob@example.com")).thenReturn(Optional.of(acc));
        var res = srv.authenticateWithPassword("bob@example.com", "1234");
        assertThat(res).isNull();
    }

    @Test
    void authenticateWithPasswordWhenCredentialsAreNull() {
        var acc = new UserAccount(1L, "bob@example.com");
        //noinspection SpellCheckingInspection
        acc.setPasswordHash("$2y$12$LBD4CPY8MaRlu45zx548Fu6Qsw5W8j5Apna5DLtRoiS3N51DZ.rUG");

        when(repo.findAccountByEmailIgnoreCase("bob@example.com")).thenReturn(Optional.of(acc));
        var res = srv.authenticateWithPassword(null, null);
        assertThat(res).isNull();
    }
}
