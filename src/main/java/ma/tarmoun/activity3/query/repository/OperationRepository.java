package ma.tarmoun.activity3.query.repository;

import ma.tarmoun.activity3.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
