package com.example.learningspring.service;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.learningspring.dto.CreateTransactionDto;
import com.example.learningspring.exception.AppException;
import com.example.learningspring.model.Transaction;
import com.example.learningspring.model.User;
import com.example.learningspring.repository.TransactionRepository;
import com.example.learningspring.repository.UserRepository;


public interface TransactionService {

        Transaction createTransaction(final CreateTransactionDto transactionData);

        Transaction retrieveTransaction(final long id);
}
