package com.sdia.ebankcqrseventsourcingaxon.commonapi.commands;

import lombok.Getter;

public class CreditAccountCommand extends BaseCommand<String>{
    private double amount;
    private String currency;

    public CreditAccountCommand(String id, double am, String currency) {
        super(id);
        this.amount = am;
        this.currency = currency;
    }
    
}
