package com.example.learningspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.learningspring.dto.CreateTransactionDto;
import com.example.learningspring.model.Transaction;
import com.example.learningspring.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<Transaction> createTransaction(
            @Valid @RequestBody final CreateTransactionDto transactionData) {
        final Transaction createdTransaction =
                transactionService.createTransaction(transactionData);

        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> retrieveTransaction(@PathVariable final String id) {
        final Transaction transaction = transactionService.retrieveTransaction(Long.parseLong(id));

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

}
