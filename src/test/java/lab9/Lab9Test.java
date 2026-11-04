package lab9;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Lab9Test {
    @Test
    void preservesTotalBalanceDuringConcurrentTransfers() throws InterruptedException {
        Bank bank = new Bank();
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            accounts.add(new Account(1_000));
        }
        int expected = bank.totalBalance(accounts);
        List<Thread> workers = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            int index = i;
            Thread worker = new Thread(() -> {
                Account from = accounts.get(index % accounts.size());
                Account to = accounts.get((index + 1) % accounts.size());
                bank.transfer(from, to, 10);
            });
            workers.add(worker);
            worker.start();
        }
        for (Thread worker : workers) {
            worker.join();
        }
        assertEquals(expected, bank.totalBalance(accounts));
    }

    @Test
    void avoidsDeadlockWhenTransfersRunInOppositeDirections() throws InterruptedException {
        Bank bank = new Bank();
        Account first = new Account(1_000);
        Account second = new Account(1_000);
        CountDownLatch start = new CountDownLatch(1);
        Thread leftToRight = new Thread(() -> transferRepeatedly(bank, first, second, start));
        Thread rightToLeft = new Thread(() -> transferRepeatedly(bank, second, first, start));
        leftToRight.start();
        rightToLeft.start();
        start.countDown();
        leftToRight.join(2_000);
        rightToLeft.join(2_000);
        assertEquals(false, leftToRight.isAlive() || rightToLeft.isAlive());
    }

    @Test
    void circularBufferBlocksAndTransfersValues() throws InterruptedException {
        BlockingCircularBuffer<Integer> buffer = new BlockingCircularBuffer<>(1);
        buffer.put(10);
        CountDownLatch taken = new CountDownLatch(1);
        Thread consumer = new Thread(() -> {
            try {
                assertEquals(10, buffer.take());
                taken.countDown();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        consumer.join(2_000);
        assertEquals(0, buffer.size());
        assertEquals(0, taken.getCount());
    }

    @Test
    void rejectsInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new Account(-1));
        assertThrows(IllegalArgumentException.class, () -> new BlockingCircularBuffer<>(0));
        assertThrows(IllegalArgumentException.class, () -> new Bank().transfer(
                new Account(1), new Account(1), -1));
    }

    private static void transferRepeatedly(
            Bank bank, Account from, Account to, CountDownLatch start) {
        try {
            start.await();
            for (int i = 0; i < 10_000; i++) {
                bank.transfer(from, to, 1);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
