package com.sdia.ebankcqrseventsourcingaxon.queries.controllers;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.queries.GetAccountQuery;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.queries.GetAllAccountsQuery;
import com.sdia.ebankcqrseventsourcingaxon.queries.entities.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/account")
@AllArgsConstructor
@Slf4j
public class AccountQueryController {
    private QueryGateway queryGateway;
    @GetMapping("/accounts")
    public List<Account> accounts(){
        return queryGateway.query(new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class)).join();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable String id){
        return queryGateway.query(new GetAccountQuery(id),
                ResponseTypes.instanceOf(Account.class)).join();
    }
}
