package springApplication.service;

import springApplication.dto.CreateTransactionDto;
import springApplication.model.Transaction;

public interface TransactionService {

        Transaction createTransaction(final CreateTransactionDto transactionData);

        Transaction retrieveTransaction(final long id);
}
