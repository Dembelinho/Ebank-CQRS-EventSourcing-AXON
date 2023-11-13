package com.sdia.ebankcqrseventsourcingaxon.commonapi.events;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.enums.AccountStatus;
import lombok.Getter;

public class AccountActivatedEvent extends BaseEvent<String> {

    @Getter private AccountStatus status;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
