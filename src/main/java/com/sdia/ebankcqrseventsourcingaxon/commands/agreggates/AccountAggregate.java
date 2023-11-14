package com.sdia.ebankcqrseventsourcingaxon.commands.agreggates;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.CreateAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.CreditAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.DebitAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.enums.AccountStatus;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountActivatedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountCreatedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountCreditedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.events.AccountDebitedEvent;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.exceptions.AmountNegativeException;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.exceptions.NotSuffusantException;
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
    @CommandHandler //cette methode va s'executer automatiquement dés que la commande est emise sur le bus de commande
    public void handle(CreditAccountCommand creditAccountCommand){ //la fct de decision
        if (creditAccountCommand.getAmount() < 0)
            throw new AmountNegativeException("Impossible !! Your given Amount is Negative..");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){ //la fct d'évolution
        this.balance+=event.getAmount();
    }
    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){ //la fct de decision
        if (debitAccountCommand.getAmount() < 0)
            throw new AmountNegativeException("Impossible !! Your given Amount is Negative..");
        if (debitAccountCommand.getAmount() > this.balance)
            throw new NotSuffusantException("Balance not sufficient ..");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){ //la fct d'évolution
        this.balance-=event.getAmount();
    }
}
