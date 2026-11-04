package lab9;

import java.util.Collection;

public final class Bank {
    public void transfer(Account from, Account to, int amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Рахунки не можуть бути null.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Сума переказу не може бути від'ємною.");
        }
        if (from == to || amount == 0) {
            return;
        }

        Account first = from.getId() < to.getId() ? from : to;
        Account second = first == from ? to : from;
        first.getLock().lock();
        second.getLock().lock();
        try {
            if (from.getBalanceWithoutLock() >= amount) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        } finally {
            second.getLock().unlock();
            first.getLock().unlock();
        }
    }

    public int totalBalance(Collection<Account> accounts) {
        if (accounts == null) {
            throw new IllegalArgumentException("Список рахунків не може бути null.");
        }
        int total = 0;
        for (Account account : accounts) {
            if (account == null) {
                throw new IllegalArgumentException("Список не може містити null.");
            }
            total += account.getBalance();
        }
        return total;
    }
}
