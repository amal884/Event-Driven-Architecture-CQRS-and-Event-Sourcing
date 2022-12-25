package ma.tarmoun.activity3.command.aggregates;

import ma.tarmoun.activity3.command.DebiteAccountCommand;
import ma.tarmoun.activity3.command.CreateAccountCommand;
import ma.tarmoun.activity3.command.CreditAccountCommand;
import ma.tarmoun.activity3.commonApi.enums.AccountState;
import ma.tarmoun.activity3.commonApi.event.AccountActivatedEvent;
import ma.tarmoun.activity3.commonApi.event.AccountCreatedEvent;
import ma.tarmoun.activity3.commonApi.event.AccountCreditedEvent;
import ma.tarmoun.activity3.commonApi.event.AccountDebitedEvent;
import ma.tarmoun.activity3.commonApi.exeptions.AmountNegativeException;
import ma.tarmoun.activity3.commonApi.exeptions.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private String id ;
    private double blance ;
    private String currency;
    private AccountState accountState ;

    public AccountAggregate() {
        // required by AXON
    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        // logic metier
        if(createAccountCommand.getInitialBalance()<0) throw new RuntimeException("Impossible .....");
        //ok
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AccountState.CREATED

        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.id = event.getId();
        this.blance= event.getInitialBalance();
        this.currency= event.getCurrency();
        this.accountState= AccountState.CREATED ;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountState.ACTIVATED
        ));

    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.accountState= event.getState();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        if(creditAccountCommand.getAmount()<0) throw  new AmountNegativeException("Amount should not be negative ");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));

    }
    @EventSourcingHandler

    public void on(AccountCreditedEvent event){
        this.blance+= event.getAmount();
    }

    @CommandHandler
    public void handle(DebiteAccountCommand creditAccountCommand){
        if(creditAccountCommand.getAmount()<0) throw  new AmountNegativeException("Amount should not be negative ");
        if(this.blance<creditAccountCommand.getAmount()) throw  new BalanceNotSufficientException("Balance not sufficitient");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));

    }
    @EventSourcingHandler

    public void on(AccountDebitedEvent event){
        this.blance-= event.getAmount();
    }



}
