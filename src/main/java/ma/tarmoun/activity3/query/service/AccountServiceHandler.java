package ma.tarmoun.activity3.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.tarmoun.activity3.commonApi.enums.OperationType;
import ma.tarmoun.activity3.commonApi.event.AccountActivatedEvent;
import ma.tarmoun.activity3.commonApi.event.AccountCreatedEvent;
import ma.tarmoun.activity3.commonApi.event.AccountCreditedEvent;
import ma.tarmoun.activity3.commonApi.event.AccountDebitedEvent;
import ma.tarmoun.activity3.commonApi.queries.GetAccountByIdQuery;
import ma.tarmoun.activity3.commonApi.queries.GetAllAccountsQuery;
import ma.tarmoun.activity3.query.entities.Account;
import ma.tarmoun.activity3.query.entities.Operation;
import ma.tarmoun.activity3.query.repository.AccountRepository;
import ma.tarmoun.activity3.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository ;

    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("*************************");
        log.info("Account Created event received");
        Account account= new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setAccountState(event.getState());
        account.setBalance(event.getInitialBalance());
        accountRepository.save(account);

    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("*************************");
        log.info("Account Activated event received");
        Account account = accountRepository.findById(event.getId()).get();
        account.setAccountState(event.getState());
        accountRepository.save(account);


    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("*************************");
        log.info("Account Debited event received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setCreatedDate(new Date()); // a ne pas faire
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("*************************");
        log.info("Account Credited event received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setCreatedDate(new Date()); // a ne pas faire
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query){
        return accountRepository.findAll() ;
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get() ;

    }


}
