package com.sdia.ebankcqrseventsourcingaxon.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    @TargetAggregateIdentifier //id de la commande represente l'identifiant de l'aggregat "compte" sur laquelle on va effectuer l'operation(la commande)
    @Getter T id;
    public BaseCommand(T id) {
        this.id = id;
    }
}
