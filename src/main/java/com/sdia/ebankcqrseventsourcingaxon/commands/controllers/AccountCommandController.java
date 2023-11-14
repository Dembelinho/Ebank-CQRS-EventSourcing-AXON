package com.sdia.ebankcqrseventsourcingaxon.commands.controllers;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.CreateAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.CreditAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.DebitAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.dtos.CreateAccountRequestDTO;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.dtos.CreditAccountRequestDTO;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.dtos.DebitAccountRequestDTO;
import lombok.AllArgsConstructor;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }
    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        return commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
    }
    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        return commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> responseEntity = new ResponseEntity<>(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
    @GetMapping("/eventStore/{accountid}")
    public Stream eventStore(@PathVariable String accountid){
        return eventStore.readEvents(accountid).asStream();
    }
}
