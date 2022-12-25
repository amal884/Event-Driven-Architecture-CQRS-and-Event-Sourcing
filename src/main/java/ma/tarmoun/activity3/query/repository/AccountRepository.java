package ma.tarmoun.activity3.query.repository;

import ma.tarmoun.activity3.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account,String> {
}
