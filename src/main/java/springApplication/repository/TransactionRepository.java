package springApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springApplication.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
