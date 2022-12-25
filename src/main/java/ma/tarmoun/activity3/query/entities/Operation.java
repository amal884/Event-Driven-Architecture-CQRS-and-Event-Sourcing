package ma.tarmoun.activity3.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tarmoun.activity3.commonApi.enums.OperationType;
import ma.tarmoun.activity3.query.entities.Account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdDate ;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type ;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account ;

}
