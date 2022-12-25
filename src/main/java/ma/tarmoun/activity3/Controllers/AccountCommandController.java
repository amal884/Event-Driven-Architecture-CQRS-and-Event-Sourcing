package ma.tarmoun.activity3.Controllers;


import lombok.AllArgsConstructor;

import ma.tarmoun.activity3.command.CreateAccountCommand;
import ma.tarmoun.activity3.command.CreditAccountCommand;
import ma.tarmoun.activity3.command.DebiteAccountCommand;
import ma.tarmoun.activity3.commonApi.dtos.CreateAccountRequestDto;
import ma.tarmoun.activity3.commonApi.dtos.CreditAccountRequestDto;
import ma.tarmoun.activity3.commonApi.dtos.DebitAccountRequestDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path="/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway ;
    private EventStore eventStore ;
    @PostMapping(path="/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDto request){

        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));

        return commandResponse;

    }

    @PutMapping(path="/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDto request){

        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getId(),
                request.getAmount(),
               request.getCurrency()

        ));

        return commandResponse;

    }
    @PutMapping(path="/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDto request){

        CompletableFuture<String> commandResponse = commandGateway.send(new DebiteAccountCommand(
                request.getId(),
                request.getAmount(),
                request.getCurrency()

        ));

        return commandResponse;

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> entity = new ResponseEntity<>(

                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return entity;

    }
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){

        return eventStore.readEvents(accountId).asStream();

    }
}
