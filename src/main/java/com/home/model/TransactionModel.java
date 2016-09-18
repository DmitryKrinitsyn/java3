package com.home.model;

import com.home.beans.Transaction;

import java.util.List;
import java.util.Optional;


public interface TransactionModel {
    StatusType storeTransaction(Long id, Transaction bean);
    Optional<Transaction> getTransaction(Long id);
    List<Long> getTransactionIdsByType(String type);
    Double getTransitiveSum(Long id);
}
