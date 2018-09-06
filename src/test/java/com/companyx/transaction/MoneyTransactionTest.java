package com.companyx.transaction;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.companyx.exception.InternalCommonException;

public class MoneyTransactionTest {

	private MoneyTransaction transaction;

	@Before
	public void setup() {
		this.transaction = new MoneyTransaction();
	}

	@Test
	public void transferTest() throws InternalCommonException {
		final BigDecimal moneyToTransfer = new BigDecimal(10);

		final String receiverAccountNumber = "1A";
		final String senderAccountNumber = "2A";
		this.transaction.transfer(receiverAccountNumber, senderAccountNumber, moneyToTransfer);

		// TODO terminar aqui
	}

	public void test() {
		//		final BigDecimal moneyToTransfer = new BigDecimal(10);
		//
		//		final String receiverAccountNumber = "1A";
		//		final String senderAccountNumber = "2A";
		//
		//		// This test will likely perform differently on different platforms.
		//		final ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		//		final Counter sync = new Counter();
		//		final Counter notSync = new Counter();
		//
		//		for (int i = 0; i < NUM_THREADS; i++) {
		//			executor.submit(new Runnable() {
		//				@Override
		//				public void run() {
		//					for (int i = 0; i < NUM_ITERATIONS; i++) {
		//						sync.incSync();
		//						notSync.inc();
		//					}
		//				}
		//			});
		//		}
		//
		//		executor.shutdown();
		//		executor.awaitTermination(5, TimeUnit.SECONDS);
		//		assertThat(sync.getValue(), is(NUM_THREADS * NUM_ITERATIONS));
		//		assertThat(notSync.getValue(), is(not(NUM_THREADS * NUM_ITERATIONS)));
	}
	//TODO create test with threads
}