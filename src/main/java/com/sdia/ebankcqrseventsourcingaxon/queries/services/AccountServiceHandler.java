package com.sdia.ebankcqrseventsourcingaxon.queries.services;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.enums.OperationType;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountActivatedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountCreatedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountCreditedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountDebitedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.queries.GetAccountQuery;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.queries.GetAllAccountsQuery;
import com.sdia.ebankcqrseventsourcingaxon.queries.entities.Account;
import com.sdia.ebankcqrseventsourcingaxon.queries.entities.Operation;
import com.sdia.ebankcqrseventsourcingaxon.queries.repositories.AccountRepository;
import com.sdia.ebankcqrseventsourcingaxon.queries.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service @AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("************** log **************");
        log.info("Account Created Event Received");

        Account account = new Account();
        account.setBalance(event.getInitialBalance());
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("************** log **************");
        log.info("Account Activated Event Received");

        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);

    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("************** log **************");
        log.info("Account Credited Event Received");
        Account account = accountRepository.findById(event.getId()).get();
        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);

        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setOperationDate(new Date()); // il faut regler ça au niveau d'écriture (Event/Command)
        operation.setOperationType(OperationType.CREDIT);
        operation.setAmount(event.getAmount());
        operationRepository.save(operation);
    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("************** log **************");
        log.info("Account Debited Event Received");

        Account account = accountRepository.findById(event.getId()).get();

        Operation operation = new Operation();
        operation.setAccount(account);
        operation.setOperationDate(new Date()); // il faut regler ça au niveau d'écriture (Event/Command)
        operation.setOperationType(OperationType.DEBIT);
        operation.setAmount(event.getAmount());
        operationRepository.save(operation);

        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountQuery query){
        return accountRepository.findById(query.getId()).get();
    }
}
