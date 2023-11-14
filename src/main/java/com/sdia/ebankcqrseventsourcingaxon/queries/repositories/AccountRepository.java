package com.sdia.ebankcqrseventsourcingaxon.queries.repositories;


import com.sdia.ebankcqrseventsourcingaxon.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
