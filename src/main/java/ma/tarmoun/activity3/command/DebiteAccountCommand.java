package ma.tarmoun.activity3.command;

import lombok.Getter;

public class DebiteAccountCommand extends BaseCommand<String>{

    @Getter private double amount ;
    @Getter private String currency;

    public DebiteAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
