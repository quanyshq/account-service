package com.demo.useraccountservice.useraccount;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
    Optional<UserAccount> findAccountByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
