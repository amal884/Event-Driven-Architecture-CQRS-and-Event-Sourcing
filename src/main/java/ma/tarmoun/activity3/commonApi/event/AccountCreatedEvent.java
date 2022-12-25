package ma.tarmoun.activity3.commonApi.event;

import lombok.Getter;
import ma.tarmoun.activity3.commonApi.enums.AccountState;

public class AccountCreatedEvent extends BaseEvent<String>{

    @Getter private double initialBalance;
    @Getter  private String currency;
    @Getter private AccountState state;


    public AccountCreatedEvent(String id, double initialBalance, String currency,AccountState state) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.state=state;
    }
}
