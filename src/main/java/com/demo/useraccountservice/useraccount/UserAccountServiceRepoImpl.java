package com.demo.useraccountservice.useraccount;

import com.demo.useraccountservice.common.EntityAlreadyExists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountServiceRepoImpl implements UserAccountService {
    private UserAccountRepository userAccountRepository;
    private PasswordEncoder passwordEncoder;

    public UserAccountServiceRepoImpl(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void registerWithPassword(UserAccount userAccount, String password) {
        if (userAccount == null) {
            throw new IllegalArgumentException("Empty user account cannot be registered.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("User account without password cannot be registered.");
        }
        if (userAccountRepository.existsByEmailIgnoreCase(userAccount.getEmail())) {
            throw new EntityAlreadyExists("Email has already been registered: " + userAccount.getEmail());
        }
        userAccount.setPasswordHash(passwordEncoder.encode(password));
        userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount authenticateWithPassword(String email, String password) {
        var acc = userAccountRepository.findAccountByEmailIgnoreCase(email);
        if (acc.isEmpty() || password == null || !passwordEncoder.matches(password, acc.get().getPasswordHash())) {
            return null;
        }
        return acc.get();
    }
}
