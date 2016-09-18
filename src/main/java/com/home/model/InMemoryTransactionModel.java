package com.home.model;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.home.beans.Transaction;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryTransactionModel implements TransactionModel {

    private Map<Long, Transaction> transactionById = new TreeMap<>();
    private ListMultimap<String, Long> transactionIdByType = ArrayListMultimap.create();
    private ListMultimap<Long, Long> transactionChildrenIdByPrentId = ArrayListMultimap.create();

    synchronized public StatusType storeTransaction(Long id, Transaction bean) {

        if( transactionById.containsKey(id)) {
            return StatusType.failed;
        }

        transactionById.put(id, bean);
        transactionIdByType.put(bean.getType(), id);

        Long parentId = bean.getParent_id();

        if(null != parentId) {
            transactionChildrenIdByPrentId.put(parentId, id);
        }

        return StatusType.ok;
    }

    synchronized public Optional<Transaction> getTransaction(Long id)
    {
        return Optional.ofNullable(transactionById.get(id));
    }

    synchronized public List<Long> getTransactionIdsByType(String type) {
        return transactionIdByType.get(type);
    }

    synchronized public Double getTransitiveSum(Long id) {
        Double result = 0d;

        Transaction transaction = transactionById.get(id);
        if(null == transaction) {
            return result;
        }

        result += transaction.getAmount();

        result += getTransitiveChildrenIds(id)
                    .parallelStream()
                    .mapToDouble( childId -> getTransaction(childId).get().getAmount() )
                    .sum();

        return result;
    }

    private List<Long> getTransitiveChildrenIds(Long id) {
        List<Long> result = new LinkedList<>();
        List<Long> children = transactionChildrenIdByPrentId.get(id);

        result.addAll(children);

        for(Long childId : children) {
            result.addAll(getTransitiveChildrenIds(childId));
        }

        return result;
    }
}
