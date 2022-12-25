package ma.tarmoun.activity3.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tarmoun.activity3.commonApi.enums.AccountState;

import javax.persistence.*;
import java.util.Collection;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountState accountState ;
    private String currency;
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operations;
}
