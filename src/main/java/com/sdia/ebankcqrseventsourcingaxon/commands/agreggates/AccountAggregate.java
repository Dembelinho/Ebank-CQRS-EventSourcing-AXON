package com.sdia.ebankcqrseventsourcingaxon.commands.agreggates;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.CreateAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.enums.AccountStatus;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountActivatedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    // Objet Aggregate représente l'état actuel de l'app
    // AggregateIdentifier => TargetAggregateIdentifier
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;
    public AccountAggregate() {
        //Required by AXON
    }
    @CommandHandler //faire un subscribe sur le bus de command et dés une
    public AccountAggregate(CreateAccountCommand command) {
        if (command.getInitialBalance() < 0)
            throw new RuntimeException("Impossible !! Initial Balance is Negative..");
        //si tout ça marche bien
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.balance=event.getInitialBalance();
        this.currency= event.getCurrency();
        this.accountId=event.getId();
        this.status=event.getStatus();
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),AccountStatus.ACTIVATED));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent activatedEvent){
        this.status=activatedEvent.getStatus();
    }
}
