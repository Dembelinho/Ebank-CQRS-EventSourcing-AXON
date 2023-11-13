package com.sdia.ebankcqrseventsourcingaxon.commands.controllers;

import com.sdia.ebankcqrseventsourcingaxon.commonapi.commands.CreateAccountCommand;
import com.sdia.ebankcqrseventsourcingaxon.commonapi.dtos.CreateAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){

        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }
}
