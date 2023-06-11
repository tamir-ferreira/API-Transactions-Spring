package springApplication.service.impl;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springApplication.dto.CreateTransactionDto;
import springApplication.exception.AppException;
import springApplication.model.Transaction;
import springApplication.model.User;
import springApplication.repository.TransactionRepository;
import springApplication.repository.UserRepository;
import springApplication.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
        private final UserRepository userRepository;
        private final TransactionRepository transactionRepository;

        public TransactionServiceImpl(UserRepository userRepository,
                        TransactionRepository transactionRepository) {
                this.userRepository = userRepository;
                this.transactionRepository = transactionRepository;
        }

        public Transaction createTransaction(final CreateTransactionDto transactionData) {
                final User foundPayer = userRepository.findById(transactionData.getPayer_id())
                                .orElseThrow(() -> new AppException("User not found",
                                                HttpStatus.NOT_FOUND));

                if (Objects.equals(foundPayer.getType(), "SELLER")) {
                        throw new AppException("SELLER type users cannot send money",
                                        HttpStatus.FORBIDDEN);
                }

                final User foundPayee = userRepository.findById(transactionData.getPayee_id())
                                .orElseThrow(() -> new AppException("User not found",
                                                HttpStatus.NOT_FOUND));

                final float transactionValue = transactionData.getValue();

                final float payerCurrentBalance = foundPayer.getBalance();

                if (payerCurrentBalance < transactionValue) {
                        throw new AppException("Payer balance not sufficient",
                                        HttpStatus.FORBIDDEN);
                }

                final float payeeCurrentBalance = foundPayee.getBalance();

                foundPayer.setBalance(payerCurrentBalance - transactionValue);
                foundPayee.setBalance(payeeCurrentBalance + transactionValue);

                final Transaction newTransaction =
                                new Transaction(foundPayer, foundPayee, transactionValue);

                return transactionRepository.save(newTransaction);
        }

        public Transaction retrieveTransaction(final long id) {
                return transactionRepository.findById(id)
                                .orElseThrow(() -> new AppException("Transaction not found",
                                                HttpStatus.NOT_FOUND));
        }
}
