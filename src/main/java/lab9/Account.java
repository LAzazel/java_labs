package lab9;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public final class Account {
    private static final AtomicLong NEXT_ID = new AtomicLong();

    private final long id = NEXT_ID.getAndIncrement();
    private final ReentrantLock lock = new ReentrantLock();
    private int balance;

    public Account(int initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Початковий баланс не може бути від'ємним.");
        }
        balance = initialBalance;
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    long getId() {
        return id;
    }

    ReentrantLock getLock() {
        return lock;
    }

    int getBalanceWithoutLock() {
        return balance;
    }

    void withdraw(int amount) {
        if (balance < amount) {
            throw new IllegalStateException("Недостатньо коштів на рахунку.");
        }
        balance -= amount;
    }

    void deposit(int amount) {
        balance += amount;
    }
}
