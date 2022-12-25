package ma.tarmoun.activity3.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class CreditAccountCommand extends BaseCommand<String>{

    @Getter private double amount ;
    @Getter private String currency;

    public CreditAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
