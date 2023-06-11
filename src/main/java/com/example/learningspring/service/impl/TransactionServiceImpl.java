package com.example.learningspring.service.impl;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.learningspring.dto.CreateTransactionDto;
import com.example.learningspring.exception.AppException;
import com.example.learningspring.model.Transaction;
import com.example.learningspring.model.User;
import com.example.learningspring.repository.TransactionRepository;
import com.example.learningspring.repository.UserRepository;
import com.example.learningspring.service.TransactionService;

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
