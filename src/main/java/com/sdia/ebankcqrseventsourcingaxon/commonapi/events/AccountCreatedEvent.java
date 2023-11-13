package com.sdia.ebankcqrseventsourcingaxon.commonapi.events;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.enums.AccountStatus;
import lombok.Getter;

public class AccountCreatedEvent extends BaseEvent<String> {
    @Getter private double initialBalance;
    @Getter private String currency;
    @Getter private AccountStatus status;

    public AccountCreatedEvent(String id, double balance, String currency, AccountStatus status) {
        super(id);
        this.initialBalance = balance;
        this.currency = currency;
        this.status = status;
    }
}
