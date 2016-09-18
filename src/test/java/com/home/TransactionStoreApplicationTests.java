package com.home;


import com.home.beans.Transaction;
import com.home.model.InMemoryTransactionModel;
import com.home.model.StatusType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class TransactionStoreApplicationTests {

	@Test
	public void testStoreTransaction() {
		InMemoryTransactionModel model = new InMemoryTransactionModel();

		Transaction transaction = new Transaction();

		transaction.setAmount(5000d);
		transaction.setType("cars");

		StatusType status = model.storeTransaction(10L, transaction);

		Assert.assertEquals(status, StatusType.ok);
	}

	@Test
	public void testStoreAndGetTransaction() {
		InMemoryTransactionModel model = new InMemoryTransactionModel();

		Transaction transaction = new Transaction();

		transaction.setAmount(5000d);
		transaction.setType("cars");

		StatusType status = model.storeTransaction(10L, transaction);

		Transaction newTransaction = model.getTransaction(10L).get();

		Assert.assertEquals(transaction.getAmount(), newTransaction.getAmount());
		Assert.assertEquals(transaction.getParent_id(), newTransaction.getParent_id());
		Assert.assertEquals(transaction.getType(), newTransaction.getType());
	}

	@Test
	public void testByTypeTransaction() {
		InMemoryTransactionModel model = new InMemoryTransactionModel();

		Transaction transaction = new Transaction();

		transaction.setAmount(5000d);
		transaction.setType("cars");

		model.storeTransaction(10L, transaction);

		Transaction transaction2 = new Transaction();

		transaction2.setAmount(10000d);
		transaction2.setType("shopping");
		transaction2.setParent_id(10L);

		model.storeTransaction(11L, transaction2);

		List<Long> cars = model.getTransactionIdsByType("cars");

		Assert.assertEquals(cars.get(0).longValue(), 10L);

	}

	@Test
	public void testSumTransaction() {
		InMemoryTransactionModel model = new InMemoryTransactionModel();

		Transaction transaction = new Transaction();

		transaction.setAmount(5000d);
		transaction.setType("cars");

		model.storeTransaction(10L, transaction);

		Transaction transaction2 = new Transaction();

		transaction2.setAmount(10000d);
		transaction2.setType("shopping");
		transaction2.setParent_id(10L);

		model.storeTransaction(11L, transaction2);

		Double result10 = model.getTransitiveSum(10L);
		Double result11 = model.getTransitiveSum(11L);

		Assert.assertEquals(result10, 15000, 0.1);
		Assert.assertEquals(result11, 10000, 0.1);
	}

}
