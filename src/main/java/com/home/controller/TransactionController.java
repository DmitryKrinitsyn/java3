package com.home.controller;

import com.home.beans.Status;
import com.home.beans.Sum;
import com.home.beans.Transaction;
import com.home.model.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactionservice")
public class TransactionController {

    @Autowired
    private TransactionModel model;

    @RequestMapping(path = "/transaction/{id}", method=RequestMethod.PUT)
    public Status storeTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction) {
        return new Status( model.storeTransaction(id, transaction) );
    }

    @RequestMapping(path = "/transaction/{id}")
    Transaction getTransaction(@PathVariable("id") Long id) {
        return model.getTransaction(id).get();
    }

    @RequestMapping(path = "/types/{type}")
    List<Long> getTransactionIdsByTypes(@PathVariable("type") String type) {
        return model.getTransactionIdsByType(type);
    }

    @RequestMapping(path = "/sum/{id}")
    Sum getSum(@PathVariable("id") Long id) {
        return new Sum( model.getTransitiveSum(id) );
    }



}