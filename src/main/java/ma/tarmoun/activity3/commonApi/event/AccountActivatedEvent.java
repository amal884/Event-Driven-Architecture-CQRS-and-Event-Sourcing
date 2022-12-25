package ma.tarmoun.activity3.commonApi.event;

import lombok.Getter;
import ma.tarmoun.activity3.commonApi.enums.AccountState;

public class AccountActivatedEvent extends BaseEvent<String> {
    @Getter
    private AccountState state;

    public AccountActivatedEvent(String id, AccountState state) {
        super(id);
        this.state = state;
    }
}
