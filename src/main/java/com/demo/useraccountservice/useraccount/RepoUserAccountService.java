package com.demo.useraccountservice.useraccount;

import com.demo.useraccountservice.common.EntityAlreadyExists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RepoUserAccountService implements UserAccountService {
    private UserAccountRepository userAccountRepository;
    private PasswordEncoder passwordEncoder;

    public RepoUserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount findByEmail(String email) {
        if (email == null) {
            return null;
        }
        var acc = userAccountRepository.findAccountByEmailIgnoreCase(email.trim());
        return acc.isEmpty() ? null : acc.get();
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
}
